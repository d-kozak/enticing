import {ContextDialogState} from "../../AppState";
import {ContextDialogAction} from "../../actions/dialog/ContextDialogActions";


type ContextDialogReducer = (state: ContextDialogState | undefined, action: ContextDialogAction) => ContextDialogState


const initialState: ContextDialogState = {
    context: null
}

const contextDialogReducer: ContextDialogReducer = (state = initialState, action) => {
    switch (action.type) {
        case "[CONTEXT DIALOG] CONTEXT LOADED":
            return {
                context: action.context
            };

        case "[CONTEXT DIALOG] CLOSED":
            return {
                context: null
            }
    }
    return state;
};

export default contextDialogReducer;