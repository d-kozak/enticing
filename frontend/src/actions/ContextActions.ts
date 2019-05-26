import {SearchResult} from "../entities/SearchResult";
import {ThunkResult} from "./RootActions";
import {mockContextRequested} from "../mocks/mockContextApi";
import {API_BASE_PATH, useMockApi} from "../globals";

import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "./ProgressBarActions";
import {searchResultUpdatedAction} from "./SearchResultActions";
import {transformSearchResult} from "./QueryActions";
import {openSnackBar} from "./SnackBarActions";

export const contextExtensionRequestAction = (searchResult: SearchResult): ThunkResult<void> => dispatch => {
    if (useMockApi()) {
        mockContextRequested(searchResult, dispatch);
        return;
    }
    dispatch(showProgressBarAction())
    axios.post(`${API_BASE_PATH}/query/context`, searchResult, {
        withCredentials: true
    }).then((response) => {
        transformSearchResult(response.data);
        const updatedResult = {...searchResult, ...response.data}
        updatedResult.snippet.text = searchResult.snippet.text + response.data.snippet.text;
        dispatch(searchResultUpdatedAction(updatedResult))
        dispatch(hideProgressBarAction());
    }).catch(() => {
        dispatch(openSnackBar('Could not extend context'));
        dispatch(hideProgressBarAction());
    })
};