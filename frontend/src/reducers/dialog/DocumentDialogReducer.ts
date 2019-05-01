import {DocumentDialogState} from "../../AppState";
import {DocumentDialogAction} from "../../actions/dialog/DocumentDialogAction";


type DocumentDialogReducer = (state: DocumentDialogState | undefined, action: DocumentDialogAction) => DocumentDialogState


const initialState: DocumentDialogState = {
    document: null
}

const documentDialogReducer: DocumentDialogReducer = (state = initialState, action) => {
    switch (action.type) {
        case "[DOCUMENT DIALOG] DOCUMENT LOADED":
            return {
                document: action.document
            };

        case "[DOCUMENT DIALOG] CLOSED":
            return {
                document: null
            }
    }
    return state;
};

export default documentDialogReducer;