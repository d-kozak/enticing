import {ThunkResult} from "./RootActions";
import {mockSearch} from "../mocks/mockSearchApi";
import * as H from "history";
import {SearchQuery} from "../entities/SearchQuery";
import {API_BASE_PATH, useMockApi} from "../globals";
import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "./ProgressBarActions";
import {newSearchResultsAction} from "./SearchResultActions";
import {openSnackBar} from "./SnackBarActions";

export const transformSearchResult = (item: any) => {
    console.log(item);
    const content = item.payload.content;
    for (let id in content.annotations) {
        const annotation = content.annotations[id];
        annotation.content = new Map(Object.entries(annotation.content))
    }
    item.snippet = {};
    item.snippet.annotations = new Map(Object.entries(content.annotations));
    item.snippet.text = content.text;
    item.snippet.positions = content.positions;
    for (let id in content.positions) {
        const position = content.positions[id];
        position.from = position.match.from;
        position.to = position.from + position.match.size;
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
        response.data.forEach((item: any, index: Number) => {
            transformSearchResult(item)
            item.id = index;
        })
        dispatch(newSearchResultsAction(response.data));
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

