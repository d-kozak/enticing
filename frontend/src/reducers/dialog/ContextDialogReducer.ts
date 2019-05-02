import {ContextDialogAction} from "../../actions/dialog/ContextDialogActions";
import {SearchResultContext} from "../../entities/SearchResultContext";


export interface ContextDialogState {
    readonly context: SearchResultContext | null
    readonly showProgress: boolean
}

const initialState: ContextDialogState = {
    context: null,
    showProgress: false
}

type ContextDialogReducer = (state: ContextDialogState | undefined, action: ContextDialogAction) => ContextDialogState

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