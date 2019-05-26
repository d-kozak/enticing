import {IndexedDocument} from "../../entities/IndexedDocument";
import {Snippet} from "../../entities/Snippet";
import {ThunkResult} from "../RootActions";
import {mockDocumentRequested} from "../../mocks/mockDocumentApi";
import {API_BASE_PATH, useMockApi} from "../../globals";
import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "../ProgressBarActions";
import {openSnackBar} from "../SnackBarActions";

export const DOCUMENT_DIALOG_DOCUMENT_LOADED = '[DOCUMENT DIALOG] DOCUMENT LOADED';
export const DOCUMENT_DIALOG_CLOSED = '[DOCUMENT DIALOG] CLOSED';

interface DocumentDialogLoadedAction {
    type: typeof DOCUMENT_DIALOG_DOCUMENT_LOADED
    document: IndexedDocument
}

interface DialogClosedAction {
    type: typeof DOCUMENT_DIALOG_CLOSED
}


export type DocumentDialogAction = DocumentDialogLoadedAction | DialogClosedAction

export const documentLoadedAction = (document: IndexedDocument): DocumentDialogLoadedAction => ({
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
    axios.post(`${API_BASE_PATH}/query/document`, {docId: searchResult.docId}, {
        withCredentials: true
    }).then(response => {
        dispatch(hideProgressBarAction());
        dispatch(documentLoadedAction(response.data));
    }).catch(() => {
        dispatch(openSnackBar('Could not load document'))
        dispatch(hideProgressBarAction());
    })
};