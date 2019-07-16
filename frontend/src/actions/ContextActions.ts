import {ThunkResult} from "./RootActions";
import {mockContextRequested} from "../mocks/mockContextApi";
import {API_BASE_PATH, useMockApi} from "../globals";

import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "./ProgressBarActions";
import {searchResultUpdatedAction} from "./SearchResultActions";
import {openSnackBar} from "./SnackBarActions";
import {Snippet} from "../entities/Snippet";
import {transformAnnotatedText} from "./QueryActions";
import {isSnippetExtension, SnippetExtension} from "../entities/SnippetExtension";
import {ContextExtensionQuery} from "../entities/ContextExtensionQuery";
import {MatchedRegion} from "../entities/Annotation";

// todo incomplete only adds text now
function mergeSnippet(snippet: Snippet, extension: SnippetExtension): Snippet {
    const {prefix, suffix, canExtend} = extension;
    const prefixSize = prefix.content.text.length;

    const newPositions = snippet.payload.content.positions.map(position => {
        return {
            ...position,
            match: new MatchedRegion(prefixSize + position.match.from, position.match.size),
            subAnnotations: position.subAnnotations != undefined ? position.subAnnotations.map(subPosition => {
                return {
                    ...subPosition,
                    match: new MatchedRegion(prefixSize + subPosition.match.from, subPosition.match.size)
                }
            }) : undefined
        }
    })

    const newQueryMapping = snippet.payload.content.queryMapping.map(mapping => {
        return {
            ...mapping,
            textIndex: new MatchedRegion(prefixSize + mapping.textIndex.from, mapping.textIndex.size)
        }
    });

    return {
        ...snippet,
        location: snippet.location - prefixSize,
        size: prefixSize + snippet.size + suffix.content.text.length,
        canExtend,
        payload: {
            content: {
                text: prefix.content.text + snippet.payload.content.text + suffix.content.text,
                positions: newPositions,
                queryMapping: newQueryMapping,
                annotations: snippet.payload.content.annotations
            }
        }
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
        transformAnnotatedText(response.data.prefix.content);
        transformAnnotatedText(response.data.suffix.content);


        const merged: Snippet = mergeSnippet(searchResult, response.data);
        dispatch(searchResultUpdatedAction(merged));
        dispatch(hideProgressBarAction());
    }).catch((error) => {
        console.error(error);
        dispatch(openSnackBar('Could not extend context'));
        dispatch(hideProgressBarAction());
    })
};