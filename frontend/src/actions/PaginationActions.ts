import {ThunkResult} from "./RootActions";
import {API_BASE_PATH} from "../globals";
import axios from "axios";
import {openSnackbar} from "../reducers/SnackBarReducer";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {isResultList} from "../entities/ResultList";
import {appendMoreSearchResults, setMoreResultsAvailable} from "../reducers/SearchResultReducer";
import {parseNewAnnotatedText} from "../components/annotations/TextUnitList";
import {PerfTimer} from "../utils/perf";

export const getMoreResults = (currentResultSize: number): ThunkResult<void> => async dispatch => {
    dispatch(showProgressbar());
    try {
        const timer = new PerfTimer("append more results");
        timer.sample("before requests");
        const response = await axios.get(`${API_BASE_PATH}/query/get_more`);
        const backendTime = timer.sample("response received");
        if (!isResultList(response.data)) {
            console.error(`Invalid search result ${JSON.stringify(response.data, null, 2)}`);
            dispatch(openSnackbar('Could not load more results'));
            dispatch(setMoreResultsAvailable(false));
            return;
        }
        for (let error in response.data.errors) {
            dispatch(openSnackbar(`Error from ${error}: ${response.data.errors[error]}`))
        }
        for (let i in response.data.searchResults) {
            const snippet = response.data.searchResults[i];
            snippet.id = `${snippet.host}:${snippet.collection}:${snippet.documentId}:${currentResultSize + i}`;
            const parsed = parseNewAnnotatedText(snippet.payload.content);
            if (parsed !== null) {
                snippet.payload.content = parsed
            } else {
                console.error("could not parse snippet " + JSON.stringify(snippet))
            }
        }
        const frontendTime = timer.sample("frontend time");
        dispatch(appendMoreSearchResults({
            searchResults: response.data.searchResults,
            hasMore: response.data.searchResults.length > 0,
            statistics: {
                backendTime,
                frontendTime
            }
        }));
        timer.finish();
        dispatch(hideProgressbar());
    } catch (e) {
        dispatch(openSnackbar('Could not load more results'));
        dispatch(setMoreResultsAvailable(false));
        dispatch(hideProgressbar());
    }
};