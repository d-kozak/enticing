import {IndexedDocument} from "../../entities/IndexedDocument";
import {SearchResult} from "../../entities/SearchResult";
import {ThunkResult} from "../RootActions";
import {mockDocumentRequested} from "../../mocks/mockDocumentApi";

interface DocumentDialogLoadedAction {
    type: '[DOCUMENT DIALOG] DOCUMENT LOADED'
    document: IndexedDocument
}

interface DialogClosedAction {
    type: '[DOCUMENT DIALOG] CLOSED'
}


export type DocumentDialogAction = DocumentDialogLoadedAction | DialogClosedAction

export const documentLoadedAction = (document: IndexedDocument): DocumentDialogLoadedAction => ({
    type: "[DOCUMENT DIALOG] DOCUMENT LOADED",
    document
});

export const documentDialogClosedAction = (): DialogClosedAction => ({
    type: "[DOCUMENT DIALOG] CLOSED"
});

export const documentDialogRequestedAction = (searchResult: SearchResult): ThunkResult<void> => dispatch => {
    mockDocumentRequested(searchResult, dispatch);
};