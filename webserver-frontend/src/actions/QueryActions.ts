import {ThunkResult} from "./RootActions";
import * as H from "history";
import {SearchQuery} from "../entities/SearchQuery";
import {API_BASE_PATH} from "../globals";
import axios, {AxiosResponse} from "axios";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {isEagerResult, isResultList, ResultList} from "../entities/ResultList";
import {SearchSettings} from "../entities/SearchSettings";
import {openSnackbar} from "../reducers/SnackBarReducer";
import {appendMoreSearchResults, newSearchResults} from "../reducers/SearchResultReducer";
import {User} from "../entities/User";
import {createMetadataRequest, filterCorpusFormat} from "./metadataFiltering";
import {PerfTimer} from "../utils/perf";
import {ApplicationState} from "../ApplicationState";
import {ThunkDispatch} from "redux-thunk";
import {AnyAction} from "redux";
import {CorpusFormat} from "../entities/CorpusFormat";
import {encodeQuery} from "../utils/queryEncoding";

/**
 * generate uuid
 */
function uuidv4() {
    // @ts-ignore
    return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
        (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    );
}

const prepareQuery: (query: string, user: User, searchSettings: SearchSettings, state: ApplicationState, dispatch: ThunkDispatch<ApplicationState, undefined, AnyAction>) => [SearchQuery, CorpusFormat] | null = (query, user, searchSettings, state, dispatch) => {
    if (!searchSettings.corpusFormat) {
        dispatch(openSnackbar('No corpus format is loaded, cannot perform search'));
        console.log('No corpus format is loaded, cannot perform search');
        return null;
    }
    const selectedMetadata = user.selectedMetadata[searchSettings.id];
    if (selectedMetadata && selectedMetadata.indexes.indexOf("token") < 0) {
        dispatch(openSnackbar('Cannot perform search without token index being selected'));
        console.log('Cannot perform search without token index being selected');
        return null;
    }
    const metadata = createMetadataRequest(searchSettings.corpusFormat, selectedMetadata);
    const filteredCorpusFormat = filterCorpusFormat(searchSettings.corpusFormat, selectedMetadata);
    const searchQuery: SearchQuery = {
        query,
        metadata,
        defaultIndex: selectedMetadata && selectedMetadata.defaultIndex || "token",
        resultFormat: "SNIPPET",
        textFormat: "TEXT_UNIT_LIST",
        snippetCount: user.userSettings.resultsPerPage,
        filterOverlaps: user.userSettings.filterOverlaps,
        uuid: uuidv4()
    };

    return [searchQuery, filteredCorpusFormat];
};

function handleErrors(response: AxiosResponse<ResultList>, dispatch: ThunkDispatch<ApplicationState, undefined, AnyAction>, searchSettings: SearchSettings) {
    const failedServers = Object.keys(response.data.errors);
    if (failedServers.length > 0) {
        const message = failedServers.map(server => `Error from ${server}: ${response.data.errors[server]}`).join('\n');
        dispatch(openSnackbar(`${failedServers.length}/${searchSettings.servers.length} servers failed on this request`));
        console.error(message);
    }
}

function mainRequest(searchSettings: SearchSettings, searchQuery: SearchQuery, timer: PerfTimer, dispatch: ThunkDispatch<ApplicationState, undefined, AnyAction>) {
    axios.post(`${API_BASE_PATH}/query?settings=${searchSettings.id}`, searchQuery, {
        withCredentials: true
    }).then(response => {
        const backendTime = timer.sample('response received');
        if (!isResultList(response.data)) {
            throw `Invalid search result ${JSON.stringify(response.data, null, 2)}`;
        }

        handleErrors(response, dispatch, searchSettings);


        const frontendTime = timer.sample('response processed, dispatching results to redux');
        dispatch(appendMoreSearchResults({
            searchResults: [],
            hasMore: response.data.hasMore,
            statistics: {
                backendTime,
                frontendTime
            }
        }));

        dispatch(hideProgressbar());
        timer.finish();
    }).catch((error) => {
        console.error(error);
        if (error.response && error.response.status == 400) {
            let msg = `Cannot search, query is not valid`;
            console.error(msg);
            dispatch(openSnackbar(msg));
        } else {
            dispatch(openSnackbar(`Could not load search results`));
        }
        dispatch(hideProgressbar());
        dispatch(appendMoreSearchResults({
            searchResults: [],
            hasMore: false
        }))
    })
}

function sleep(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

export const startSearchingAction = (query: string, user: User, searchSettings: SearchSettings, history?: H.History): ThunkResult<void> => async (dispatch, getState) => {
    const state = getState();
    const res = prepareQuery(query, user, searchSettings, state, dispatch);
    if (res == null) return;
    const [searchQuery, filteredCorpusFormat] = res;
    dispatch(showProgressbar());
    const timer = new PerfTimer('SearchQuery');
    timer.sample('before request');

    dispatch(newSearchResults({
        query,
        searchResults: [],
        corpusFormat: filteredCorpusFormat,
        moreResultsAvailable: true
    }));

    mainRequest(searchSettings, searchQuery, timer, dispatch);

    let redirected = false;

    try {
        await sleep(300);
        let resultCnt = 0;
        while (true) {
            console.log("Waiting for more results...");
            let response = await axios.get(`${API_BASE_PATH}/query/storage/${searchQuery.uuid}`, {
                withCredentials: true
            });
            if (!isEagerResult(response.data))
                throw `Invalid search result ${JSON.stringify(response.data, null, 2)}`;

            for (let i in response.data.searchResults) {
                const snippet = response.data.searchResults[i];
                snippet.id = `${snippet.host}:${snippet.collection}:${snippet.documentId}:${resultCnt + i}`;
            }
            resultCnt += response.data.searchResults.length;

            dispatch(appendMoreSearchResults({
                searchResults: response.data.searchResults,
                hasMore: response.data.state != "FINISHED"
            }));

            if (history && response.data.searchResults.length > 0 && !redirected) {
                history.push(`/search?query=${encodeQuery(query)}`);
                redirected = true;
            }

            if (response.data.state === "FINISHED") break;
            await sleep(500);
        }

    } catch (e) {
        console.error(e);
        dispatch(openSnackbar(`Failed to load results`));
    }

    if (history && !redirected) {
        history.push(`/search?query=${encodeQuery(query)}`);
    }
};


