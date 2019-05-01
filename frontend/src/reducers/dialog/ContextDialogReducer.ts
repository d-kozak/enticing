import {ContextDialogState} from "../../AppState";
import {ContextDialogAction} from "../../actions/dialog/ContextDialogActions";


type ContextDialogReducer = (state: ContextDialogState | undefined, action: ContextDialogAction) => ContextDialogState


const initialState: ContextDialogState = {
    context: null,
    showProgress: false
}

const contextDialogReducer: ContextDialogReducer = (state = initialState, action) => {
    switch (action.type) {
        case "[CONTEXT DIALOG] CONTEXT LOADED":
            return {
                context: action.context,
                showProgress: false
            };

        case "[CONTEXT DIALOG] CLOSED":
            return {
                context: null,
                showProgress: false
            }
        case "[CONTEXT DIALOG] CONTEXT EXTENDED":
            return {
                ...state,
                context: action.context
            }
        case "[CONTEXT DIALOG] SHOW PROGRESS":
            return {
                ...state,
                showProgress: true
            }
        case "[CONTEXT DIALOG] HIDE PROGRESS":
            return {
                ...state,
                showProgress: false
            }
    }
    return state;
};

export default contextDialogReducer;