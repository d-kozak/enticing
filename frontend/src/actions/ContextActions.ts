import {ThunkResult} from "./RootActions";
import {API_BASE_PATH} from "../globals";

import axios from "axios";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {SearchResult} from "../entities/SearchResult";
import {isSnippetExtension, SnippetExtension} from "../entities/SnippetExtension";
import {ContextExtensionQuery} from "../entities/ContextExtensionQuery";
import {parseNewAnnotatedText, TextUnitList} from "../components/annotations/TextUnitList";
import {openSnackbar} from "../reducers/SnackBarReducer";

import {updateSearchResult} from "../reducers/SearchResultReducer";

function mergeSnippet(searchResult: SearchResult, data: SnippetExtension): SearchResult {
    const newLocation = data.prefix.location;
    const newSize = data.prefix.size + searchResult.payload.size + data.suffix.size;
    const newText = new TextUnitList([
        ...data.prefix.content.content,
        ...searchResult.payload.content.content,
        ...data.suffix.content.content
    ]);

    return {
        ...searchResult,
        payload: {
            content: newText,
            location: newLocation,
            size: newSize,
            canExtend: data.canExtend
        }
    };
}

export const contextExtensionRequestAction = (searchResult: SearchResult): ThunkResult<void> => dispatch => {
    dispatch(showProgressbar());
    const query: ContextExtensionQuery = {
        host: searchResult.host,
        collection: searchResult.collection,
        documentId: searchResult.documentId,
        defaultIndex: "token",
        location: searchResult.payload.location,
        size: searchResult.payload.size,
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


        const merged: SearchResult = mergeSnippet(searchResult, response.data);
        dispatch(updateSearchResult(merged));
        dispatch(hideProgressbar());
    }).catch((error) => {
        console.error(error);
        dispatch(openSnackbar('Could not extend context'));
        dispatch(hideProgressbar());
    })
};