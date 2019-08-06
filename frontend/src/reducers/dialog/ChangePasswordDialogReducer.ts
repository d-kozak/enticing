import {
    CHANGE_PASSWORD_DIALOG_CLOSE,
    CHANGE_PASSWORD_DIALOG_HIDE_PROGRESS,
    CHANGE_PASSWORD_DIALOG_OPEN,
    CHANGE_PASSWORD_DIALOG_SHOW_PROGRESS,
    ChangePasswordDialogAction
} from "../../actions/dialog/ChangePasswordDialogActions";
import {ChangePasswordDialogState, initialState} from "../ApplicationState";


type ChangePasswordDialogReducer = (state: ChangePasswordDialogState | undefined, action: ChangePasswordDialogAction) => ChangePasswordDialogState

const changePasswordDialogReducer: ChangePasswordDialogReducer = (state = initialState.dialog.changePasswordDialog, action) => {
    switch (action.type) {
        case CHANGE_PASSWORD_DIALOG_OPEN:
            return {
                user: action.user,
                showProgress: false
            };
        case CHANGE_PASSWORD_DIALOG_CLOSE:
            return {
                user: null,
                showProgress: false
            };
        case CHANGE_PASSWORD_DIALOG_SHOW_PROGRESS:
            return {
                ...state,
                showProgress: true
            }
        case CHANGE_PASSWORD_DIALOG_HIDE_PROGRESS:
            return {
                ...state,
                showProgress: false
            };
    }
    return state;
};

export default changePasswordDialogReducer;