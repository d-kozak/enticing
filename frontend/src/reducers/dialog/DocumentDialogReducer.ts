import {
    DOCUMENT_DIALOG_CLOSED,
    DOCUMENT_DIALOG_DOCUMENT_LOADED,
    DocumentDialogAction
} from "../../actions/dialog/DocumentDialogAction";
import {IndexedDocument} from "../../entities/IndexedDocument";


export type DocumentDialogState = Readonly<{
    document: IndexedDocument | null
}>

const initialState: DocumentDialogState = {
    document: null
}

type DocumentDialogReducer = (state: DocumentDialogState | undefined, action: DocumentDialogAction) => DocumentDialogState

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