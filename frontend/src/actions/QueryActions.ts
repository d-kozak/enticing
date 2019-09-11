import {ThunkResult} from "./RootActions";
import * as H from "history";
import {SearchQuery} from "../entities/SearchQuery";
import {API_BASE_PATH} from "../globals";
import axios from "axios";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {isResultList} from "../entities/ResultList";
import {parseNewAnnotatedText} from "../components/annotations/TextUnitList";
import {SearchSettings} from "../entities/SearchSettings";
import {openSnackbar} from "../reducers/SnackBarReducer";
import {newSearchResults} from "../reducers/SearchResultReducer";
import {User} from "../entities/User";
import {createMetadataRequest, filterCorpusFormat} from "./metadataFiltering";


export const startSearchingAction = (query: string, user: User, searchSettings: SearchSettings, history?: H.History): ThunkResult<void> => (dispatch) => {
    if (!searchSettings.corpusFormat) {
        console.log('No corpus format is loaded, cannot perform search');
        return
    }
    const metadata = createMetadataRequest(searchSettings.corpusFormat, user.selectedMetadata[searchSettings.id]);
    const filteredCorpusFormat = filterCorpusFormat(searchSettings.corpusFormat, user.selectedMetadata[searchSettings.id]);
    const searchQuery: SearchQuery = {
        query,
        metadata,
        defaultIndex: "token",
        resultFormat: "SNIPPET",
        textFormat: "TEXT_UNIT_LIST",
        snippetCount: user.userSettings.resultsPerPage
    };
    dispatch(showProgressbar());
    axios.post(`${API_BASE_PATH}/query?settings=${searchSettings.id}`, searchQuery, {
        withCredentials: true
    }).then(response => {
        if (!isResultList(response.data)) {
            throw `Invalid search result ${JSON.stringify(response.data, null, 2)}`;
        }
        for (let error in response.data.errors) {
            dispatch(openSnackbar(`Error from ${error}: ${response.data.errors[error]}`))
        }
        for (let i in response.data.searchResults) {
            const snippet = response.data.searchResults[i];
            snippet.id = `${snippet.host}:${snippet.collection}:${snippet.documentId}:${i}`;
            const parsed = parseNewAnnotatedText(snippet.payload.content);
            if (parsed !== null) {
                snippet.payload.content = parsed
            } else {
                console.error("could not parse snippet " + JSON.stringify(snippet))
            }
        }

        dispatch(newSearchResults({
            snippets: response.data.searchResults,
            corpusFormat: filteredCorpusFormat,
            moreResultsAvailable: response.data.searchResults.length > 0
        }));

        dispatch(hideProgressbar());
        if (history) {
            history.push(`/search?query=${encodeURI(query)}`);
        }
    }).catch((error) => {
        console.error(error);
        dispatch(openSnackbar(`Could not load search results`));
        dispatch(hideProgressbar());
    })
};


