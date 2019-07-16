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
import {AnnotatedText, MatchedRegion} from "../entities/Annotation";


/**
 * Preprocess annotated text
 *
 * Currently it transforms all matched regions to the MatchRegion object
 * @param text to process
 */
export const transformAnnotatedText = (text: AnnotatedText) => {
    for (let position of text.positions) {
        const {from, size} = position.match
        position.match = new MatchedRegion(from, size);
        if (position.subAnnotations) {
            for (let annotation of position.subAnnotations) {
                const {from, size} = annotation.match;
                annotation.match = new MatchedRegion(from, size);
            }
        }
    }

    for (let mapping of text.queryMapping) {
        mapping.queryIndex = new MatchedRegion(mapping.queryIndex.from, mapping.queryIndex.size);
        mapping.textIndex = new MatchedRegion(mapping.textIndex.from, mapping.textIndex.size);
    }
}

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
        for (let snippet of response.data.snippets) {
            snippet.id = `${snippet.host}:${snippet.collection}:${snippet.documentId}`
            transformAnnotatedText(snippet.payload.content)
        }
        for (let error in response.data.errors) {
            dispatch(openSnackBar(`Error from ${error}: ${response.data.errors[error]}`))
        }
        dispatch(newSearchResultsAction(response.data.snippets));
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

