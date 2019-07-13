import {ThunkResult} from "./RootActions";
import {mockContextRequested} from "../mocks/mockContextApi";
import {API_BASE_PATH, useMockApi} from "../globals";

import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "./ProgressBarActions";
import {searchResultUpdatedAction} from "./SearchResultActions";
import {openSnackBar} from "./SnackBarActions";
import {Snippet} from "../entities/Snippet";
import {transformAnnotatedText} from "./QueryActions";
import {isSnippetExtension} from "../entities/SnippetExtension";

export const contextExtensionRequestAction = (searchResult: Snippet): ThunkResult<void> => dispatch => {
    if (useMockApi()) {
        mockContextRequested(searchResult, dispatch);
        return;
    }
    dispatch(showProgressBarAction())
    axios.post(`${API_BASE_PATH}/query/context`, searchResult, {
        withCredentials: true
    }).then((response) => {
        if (!isSnippetExtension(response.data)) {
            throw `Invalid document ${JSON.stringify(response.data, null, 2)}`;
        }
        transformAnnotatedText(response.data.prefix.content);
        transformAnnotatedText(response.data.suffix.content);
        const updatedResult = searchResult;
        // todo perform merge
        console.error('merge for snippet extension not implemented yet');
        // const updatedResult = {...searchResult, ...response.data}
        // updatedResult.snippet.text = searchResult.payload.text + response.data.snippet.text;
        dispatch(searchResultUpdatedAction(updatedResult))
        dispatch(hideProgressBarAction());
    }).catch(() => {
        dispatch(openSnackBar('Could not extend context'));
        dispatch(hideProgressBarAction());
    })
};