import {DocumentDialogState} from "../../AppState";
import {
    DOCUMENT_DIALOG_CLOSED,
    DOCUMENT_DIALOG_DOCUMENT_LOADED,
    DocumentDialogAction
} from "../../actions/dialog/DocumentDialogAction";


type DocumentDialogReducer = (state: DocumentDialogState | undefined, action: DocumentDialogAction) => DocumentDialogState


const initialState: DocumentDialogState = {
    document: null
}

const documentDialogReducer: DocumentDialogReducer = (state = initialState, action) => {
    switch (action.type) {
        case DOCUMENT_DIALOG_DOCUMENT_LOADED:
            return {
                document: action.document
            };

        case DOCUMENT_DIALOG_CLOSED:
            return {
                document: null
            }
    }
    return state;
};

export default documentDialogReducer;