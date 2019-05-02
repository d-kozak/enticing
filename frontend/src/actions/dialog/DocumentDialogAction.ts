import {IndexedDocument} from "../../entities/IndexedDocument";
import {SearchResult} from "../../entities/SearchResult";
import {ThunkResult} from "../RootActions";
import {mockDocumentRequested} from "../../mocks/mockDocumentApi";

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

export const documentDialogRequestedAction = (searchResult: SearchResult): ThunkResult<void> => dispatch => {
    mockDocumentRequested(searchResult, dispatch);
};