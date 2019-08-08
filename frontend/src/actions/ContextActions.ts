import {ThunkResult} from "./RootActions";
import {API_BASE_PATH} from "../globals";

import axios from "axios";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {Snippet} from "../entities/Snippet";
import {isSnippetExtension, SnippetExtension} from "../entities/SnippetExtension";
import {ContextExtensionQuery} from "../entities/ContextExtensionQuery";
import {NewAnnotatedText, parseNewAnnotatedText} from "../components/annotations/NewAnnotatedText";
import {openSnackbar} from "../reducers/SnackBarReducer";

import {updateSearchResult} from "../reducers/SearchResultReducer";

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
    dispatch(showProgressbar());
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
        dispatch(updateSearchResult(merged));
        dispatch(hideProgressbar());
    }).catch((error) => {
        console.error(error);
        dispatch(openSnackbar('Could not extend context'));
        dispatch(hideProgressbar());
    })
};