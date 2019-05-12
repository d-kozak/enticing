import {ThunkResult} from "./RootActions";
import {mockSearch} from "../mocks/mockSearchApi";
import * as H from "history";
import {SearchQuery} from "../entities/SearchQuery";
import {API_BASE_PATH, useMockApi} from "../globals";
import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "./ProgressBarActions";
import {newSearchResultsAction} from "./SearchResultActions";
import {openSnackBar} from "./SnackBarActions";


export const objectToIntMap = (obj: any): Map<number, any> => {
    const map = new Map<number, any>()
    for (let key in obj)
        map.set(Number(key), obj[key]);
    return map
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
        console.log(response);
        response.data.forEach((item: any, index: Number) => {
            for (let id in item.snippet.annotations) {
                const annotation = item.snippet.annotations[id]
                annotation.content = new Map(Object.entries(annotation.content))
            }
            item.snippet.annotations = objectToIntMap(item.snippet.annotations);
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

