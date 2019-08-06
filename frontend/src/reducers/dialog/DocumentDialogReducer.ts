import {
    DOCUMENT_DIALOG_CLOSED,
    DOCUMENT_DIALOG_DOCUMENT_LOADED,
    DocumentDialogAction
} from "../../actions/dialog/DocumentDialogAction";
import {DocumentDialogState, initialState} from "../ApplicationState";


type DocumentDialogReducer = (state: DocumentDialogState | undefined, action: DocumentDialogAction) => DocumentDialogState

const documentDialogReducer: DocumentDialogReducer = (state = initialState.dialog.documentDialog, action) => {
    switch (action.type) {
        case DOCUMENT_DIALOG_DOCUMENT_LOADED:
            return {
                document: action.document,
                corpusFormat: action.corpusFormat
            };

        case DOCUMENT_DIALOG_CLOSED:
            return {
                document: null,
                corpusFormat: null
            }
    }
    return state;
};

export default documentDialogReducer;