import {ThunkResult} from "./RootActions";
import * as H from "history";
import {SearchQuery} from "../entities/SearchQuery";
import {API_BASE_PATH} from "../globals";
import axios from "axios";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {isResultList} from "../entities/ResultList";
import {SearchSettings} from "../entities/SearchSettings";
import {openSnackbar} from "../reducers/SnackBarReducer";
import {newSearchResults} from "../reducers/SearchResultReducer";
import {User} from "../entities/User";
import {createMetadataRequest, filterCorpusFormat} from "./metadataFiltering";
import {PerfTimer} from "../utils/perf";
import {emptyTextUnitList, parseNewAnnotatedText} from "../components/annotations/TextUnitList";


export const startSearchingAction = (query: string, user: User, searchSettings: SearchSettings, history?: H.History): ThunkResult<void> => (dispatch, getState) => {
    const resultsPerPage = getState().userState.user.userSettings.resultsPerPage;
    if (!searchSettings.corpusFormat) {
        dispatch(openSnackbar('No corpus format is loaded, cannot perform search'));
        console.log('No corpus format is loaded, cannot perform search');
        return
    }
    const selectedMetadata = user.selectedMetadata[searchSettings.id];
    if (selectedMetadata && selectedMetadata.indexes.indexOf("token") < 0) {
        dispatch(openSnackbar('Cannot perform search without token index being selected'));
        console.log('Cannot perform search without token index being selected');
        return;
    }
    const metadata = createMetadataRequest(searchSettings.corpusFormat, selectedMetadata);
    const filteredCorpusFormat = filterCorpusFormat(searchSettings.corpusFormat, selectedMetadata);
    const searchQuery: SearchQuery = {
        query,
        metadata,
        defaultIndex: selectedMetadata && selectedMetadata.defaultIndex || "token",
        resultFormat: "SNIPPET",
        textFormat: "TEXT_UNIT_LIST",
        snippetCount: user.userSettings.resultsPerPage
    };
    dispatch(showProgressbar());
    const timer = new PerfTimer('SearchQuery');
    timer.sample('before request');
    const startTime = performance.now();
    axios.post(`${API_BASE_PATH}/query?settings=${searchSettings.id}`, searchQuery, {
        withCredentials: true
    }).then(response => {
        const backendTime = timer.sample('response received');
        if (!isResultList(response.data)) {
            throw `Invalid search result ${JSON.stringify(response.data, null, 2)}`;
        }

        const failedServers = Object.keys(response.data.errors);
        if (failedServers.length > 0) {
            const message = failedServers.map(server => `Error from ${server}: ${response.data.errors[server]}`).join('\n');
            dispatch(openSnackbar(`${failedServers.length}/${searchSettings.servers.length} servers failed on this request`));
            console.error(message);
        }

        for (let i in response.data.searchResults) {
            const snippet = response.data.searchResults[i];
            snippet.id = `${snippet.host}:${snippet.collection}:${snippet.documentId}:${i}`;
            if (Number(i) < resultsPerPage) {
                const parsed = parseNewAnnotatedText(snippet.payload.content);
                if (parsed != null) {
                    snippet.payload.parsedContent = parsed;
                    snippet.payload.content = emptyTextUnitList
                }
            }
        }
        const frontendTime = timer.sample('response processed, dispatching results to redux');
        dispatch(newSearchResults({
            searchResults: response.data.searchResults,
            corpusFormat: filteredCorpusFormat,
            moreResultsAvailable: response.data.searchResults.length > 0,
            statistics: {
                backendTime,
                frontendTime
            }
        }));

        dispatch(hideProgressbar());
        if (history) {
            history.push(`/search?query=${encodeURI(query)}`);
        }
        timer.finish();
    }).catch((error) => {
        console.error(error);
        dispatch(openSnackbar(`Could not load search results`));
        dispatch(hideProgressbar());
    })
};


