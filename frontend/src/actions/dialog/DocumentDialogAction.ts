import {FullDocument, isDocument} from "../../entities/FullDocument";
import {ThunkResult} from "../RootActions";
import {mockDocumentRequested} from "../../mocks/mockDocumentApi";
import {API_BASE_PATH, useMockApi} from "../../globals";
import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "../ProgressBarActions";
import {openSnackBar} from "../SnackBarActions";
import {Snippet} from "../../entities/Snippet";
import {DocumentQuery} from "../../entities/DocumentQuery";
import {parseNewAnnotatedText} from "../../components/annotations/new/NewAnnotatedText";

export const DOCUMENT_DIALOG_DOCUMENT_LOADED = '[DOCUMENT DIALOG] DOCUMENT LOADED';
export const DOCUMENT_DIALOG_CLOSED = '[DOCUMENT DIALOG] CLOSED';

interface DocumentDialogLoadedAction {
    type: typeof DOCUMENT_DIALOG_DOCUMENT_LOADED
    document: FullDocument
}

interface DialogClosedAction {
    type: typeof DOCUMENT_DIALOG_CLOSED
}


export type DocumentDialogAction = DocumentDialogLoadedAction | DialogClosedAction

export const documentLoadedAction = (document: FullDocument): DocumentDialogLoadedAction => ({
    type: DOCUMENT_DIALOG_DOCUMENT_LOADED,
    document
});

export const documentDialogClosedAction = (): DialogClosedAction => ({
    type: DOCUMENT_DIALOG_CLOSED
});

export const documentDialogRequestedAction = (searchResult: Snippet): ThunkResult<void> => dispatch => {
    if (useMockApi()) {
        mockDocumentRequested(searchResult, dispatch);
        return;
    }
    dispatch(showProgressBarAction())
    const documentQuery: DocumentQuery = {
        host: searchResult.host,
        collection: searchResult.collection,
        documentId: searchResult.documentId,
        defaultIndex: "token",
    }
    axios.post(`${API_BASE_PATH}/query/document`, documentQuery, {
        withCredentials: true
    }).then(response => {
        if (!isDocument(response.data)) {
            throw `Invalid document ${JSON.stringify(response.data, null, 2)}`;
        }
        const parsed = parseNewAnnotatedText(response.data.payload.content);
        if (parsed == null)
            throw "could not parse"
        response.data.payload.content = parsed
        dispatch(hideProgressBarAction());
        dispatch(documentLoadedAction(response.data));
    }).catch(() => {
        dispatch(openSnackBar('Could not load document'))
        dispatch(hideProgressBarAction());
    })
};