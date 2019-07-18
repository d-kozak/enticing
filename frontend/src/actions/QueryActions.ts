import {ThunkResult} from "./RootActions";
import {mockSearch} from "../mocks/mockSearchApi";
import * as H from "history";
import {SearchQuery} from "../entities/SearchQuery";
import {API_BASE_PATH, useMockApi} from "../globals";
import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "./ProgressBarActions";
import {newSearchResultsAction} from "./SearchResultActions";
import {openSnackBar} from "./SnackBarActions";
import {isSearchResult} from "../entities/SearchResult";
import {parseNewAnnotatedText} from "../components/annotations/new/NewAnnotatedText";


export const startSearchingAction = (query: SearchQuery, selectedSettings: Number, history?: H.History): ThunkResult<void> => (dispatch) => {
    const encodedQuery = encodeURI(query)
    if (useMockApi()) {
        mockSearch(query, dispatch, history)
        return;
    }
    dispatch(showProgressBarAction())
    axios.get(`${API_BASE_PATH}/query`, {
        params: {
            query: encodedQuery,
            settings: selectedSettings
        },
        withCredentials: true
    }).then(response => {
        if (!isSearchResult(response.data)) {
            throw `Invalid search result ${JSON.stringify(response.data, null, 2)}`;
        }
        for (let error in response.data.errors) {
            dispatch(openSnackBar(`Error from ${error}: ${response.data.errors[error]}`))
        }
        for (let snippet of response.data.snippets) {
            snippet.id = `${snippet.host}:${snippet.collection}:${snippet.documentId}`
        }
        const snippets = response.data.snippets.map(
            item => parseNewAnnotatedText(item)
        ).filter(item => item != null)

        // @ts-ignore
        dispatch(newSearchResultsAction(snippets));
        dispatch(hideProgressBarAction());
        if (history) {
            history.push(`/search?query=${encodedQuery}`);
        }
    }).catch((error) => {
        console.error(error);
        dispatch(openSnackBar(`Could not load search results`));
        dispatch(hideProgressBarAction());
    })
}

