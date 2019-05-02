import {SnackBarState} from "../AppState";
import {SNACKBAR_CLOSE, SNACKBAR_OPEN, SnackBarAction} from "../actions/SnackBarActions";


type SnackBarReducer = (state: SnackBarState | undefined, action: SnackBarAction) => SnackBarState

const initialState: SnackBarState = {
    isOpen: false,
    message: ''
}

const SnackBarReducer: SnackBarReducer = (state = initialState, action) => {
    switch (action.type) {
        case SNACKBAR_OPEN:
            return {
                isOpen: true,
                message: action.message
            }
        case SNACKBAR_CLOSE:
            return {
                isOpen: false,
                message: ''
            }
    }
    return state
}


export default SnackBarReducer;
