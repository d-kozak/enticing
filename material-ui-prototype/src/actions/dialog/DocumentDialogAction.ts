import {IndexedDocument} from "../../entities/IndexedDocument";
import {SearchResult} from "../../entities/SearchResult";
import {ThunkResult} from "../RootAction";
import {mockDocumentRequested} from "../../mocks/searchApi";

interface DocumentLoadedAction {
    type: '[DOCUMENT DIALOG] DOCUMENT LOADED'
    document: IndexedDocument
}

interface DialogClosedAction {
    type: '[DOCUMENT DIALOG] CLOSED'
}


export type DocumentDialogAction = DocumentLoadedAction | DialogClosedAction

export const documentLoadedAction = (document: IndexedDocument): DocumentLoadedAction => ({
    type: "[DOCUMENT DIALOG] DOCUMENT LOADED",
    document
});

export const dialogClosedAction = (): DialogClosedAction => ({
    type: "[DOCUMENT DIALOG] CLOSED"
});

export const documentDialogRequestedAction = (searchResult: SearchResult): ThunkResult<void> => dispatch => {
    mockDocumentRequested(searchResult, dispatch);
};