import {ThunkResult} from "./RootActions";
import {API_BASE_PATH} from "../globals";

import axios from "axios";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {SearchResult} from "../entities/SearchResult";
import {isSnippetExtension, SnippetExtension} from "../entities/SnippetExtension";
import {ContextExtensionQuery} from "../entities/ContextExtensionQuery";
import {emptyTextUnitList, parseNewAnnotatedText, TextUnitList} from "../components/annotations/TextUnitList";
import {openSnackbar} from "../reducers/SnackBarReducer";

import {updateSearchResult} from "../reducers/SearchResultReducer";
import {PerfTimer} from "../utils/perf";
import {createMetadataRequest} from "./metadataFiltering";
import {getSelectedSearchSettings} from "../reducers/selectors";

function mergeSnippet(searchResult: SearchResult, data: SnippetExtension): SearchResult {
    const newLocation = data.prefix.location;
    const newSize = data.prefix.size + searchResult.payload.size + data.suffix.size;
    const newText = new TextUnitList([
        ...data.prefix.parsedContent!.content,
        ...searchResult.payload.parsedContent!.content,
        ...data.suffix.parsedContent!.content
    ]);

    return {
        ...searchResult,
        payload: {
            content: emptyTextUnitList,
            parsedContent: newText,
            location: newLocation,
            size: newSize,
            canExtend: data.canExtend
        }
    };
}

export const contextExtensionRequestAction = (searchResult: SearchResult): ThunkResult<void> => (dispatch, getState) => {
    const appState = getState();
    const selectedSearchSettings = getSelectedSearchSettings(appState);
    if (selectedSearchSettings == null) {
        console.error('could not extend search result, no selected search settings');
        return;
    }
    if (!searchResult.payload.parsedContent) {
        console.error('could not extend search result which has not been parsed yet');
        return;
    }
    if (selectedSearchSettings.corpusFormat == null) {
        console.error('could not extend search result, no corpus format for selected search settings');
        return;
    }
    const user = appState.userState.user;
    dispatch(showProgressbar());
    const selectedMetadata = user.selectedMetadata[selectedSearchSettings.id];
    const metadataRequest = createMetadataRequest(selectedSearchSettings.corpusFormat, selectedMetadata);
    const query: ContextExtensionQuery = {
        query: appState.searchResult.query,
        host: searchResult.host,
        collection: searchResult.collection,
        documentId: searchResult.documentId,
        defaultIndex: selectedMetadata && selectedMetadata.defaultIndex || "token",
        metadata: metadataRequest,
        location: searchResult.payload.location,
        size: searchResult.payload.size,
        extension: 20,
    };
    const timer = new PerfTimer('ContextExtensionRequest');
    timer.sample('before request');
    const startTime = performance.now();
    axios.post(`${API_BASE_PATH}/query/context`, query, {
        withCredentials: true
    }).then((response) => {
        timer.sample('response received');
        if (!isSnippetExtension(response.data)) {
            throw `Invalid document ${JSON.stringify(response.data, null, 2)}`;
        }

        const parsedPrefix = parseNewAnnotatedText(response.data.prefix.content);
        const parsedSuffix = parseNewAnnotatedText(response.data.suffix.content);
        if (parsedPrefix === null || parsedSuffix === null) {
            throw "could not parse"
        }
        response.data.prefix.parsedContent = parsedPrefix;
        response.data.suffix.parsedContent = parsedSuffix;

        timer.sample('parsing done');
        const merged: SearchResult = mergeSnippet(searchResult, response.data);
        timer.sample('merging done, dispatching result');
        dispatch(updateSearchResult(merged));
        dispatch(hideProgressbar());
    }).catch((error) => {
        console.error(error);
        dispatch(openSnackbar('Could not extend context'));
        dispatch(hideProgressbar());
    })
};