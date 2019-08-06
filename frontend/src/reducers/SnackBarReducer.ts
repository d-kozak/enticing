import {SNACKBAR_CLOSE, SNACKBAR_OPEN, SnackBarAction} from "../actions/SnackBarActions";
import {initialState, SnackBarState} from "./ApplicationState";


type SnackBarReducer = (state: SnackBarState | undefined, action: SnackBarAction) => SnackBarState

const SnackBarReducer: SnackBarReducer = (state = initialState.snackBar, action) => {
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
