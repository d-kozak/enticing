import {ThunkResult} from "./RootActions";
import {mockContextRequested} from "../mocks/mockContextApi";
import {API_BASE_PATH, useMockApi} from "../globals";

import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "./ProgressBarActions";
import {searchResultUpdatedAction} from "./SearchResultActions";
import {openSnackBar} from "./SnackBarActions";
import {Snippet} from "../entities/Snippet";
import {isSnippetExtension, SnippetExtension} from "../entities/SnippetExtension";
import {ContextExtensionQuery} from "../entities/ContextExtensionQuery";
import {NewAnnotatedText, parseNewAnnotatedText} from "../components/annotations/new/NewAnnotatedText";


function mergeSnippet(searchResult: Snippet, data: SnippetExtension): Snippet {
    const prefix = data.prefix.content
    const suffix = data.suffix.content
    const newText = new NewAnnotatedText([
        ...prefix.content,
        ...searchResult.payload.content.content,
        ...suffix.content
    ])

    return {
        ...searchResult,
        payload: {
            content: newText
        },
        location: searchResult.location - prefix.size(),
        size: prefix.size() + searchResult.size + suffix.size(),
        canExtend: data.canExtend
    };
}

export const contextExtensionRequestAction = (searchResult: Snippet): ThunkResult<void> => dispatch => {
    if (useMockApi()) {
        mockContextRequested(searchResult, dispatch);
        return;
    }
    dispatch(showProgressBarAction())
    const query: ContextExtensionQuery = {
        host: searchResult.host,
        collection: searchResult.collection,
        documentId: searchResult.documentId,
        defaultIndex: "token",
        location: searchResult.location,
        size: searchResult.size,
        extension: 20,
    };
    axios.post(`${API_BASE_PATH}/query/context`, query, {
        withCredentials: true
    }).then((response) => {
        if (!isSnippetExtension(response.data)) {
            throw `Invalid document ${JSON.stringify(response.data, null, 2)}`;
        }

        const parsedPrefix = parseNewAnnotatedText(response.data.prefix.content)
        const parsedSuffix = parseNewAnnotatedText(response.data.suffix.content)
        if (parsedPrefix === null || parsedSuffix === null) {
            throw "could not parse"
        }
        response.data.prefix.content = parsedPrefix
        response.data.suffix.content = parsedSuffix


        const merged: Snippet = mergeSnippet(searchResult, response.data);
        dispatch(searchResultUpdatedAction(merged));
        dispatch(hideProgressBarAction());
    }).catch((error) => {
        console.error(error);
        dispatch(openSnackBar('Could not extend context'));
        dispatch(hideProgressBarAction());
    })
};