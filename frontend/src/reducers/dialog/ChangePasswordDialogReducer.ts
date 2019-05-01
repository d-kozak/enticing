import {ChangePasswordDialogState} from "../../AppState";
import {ChangePasswordDialogAction} from "../../actions/dialog/ChangePasswordDialogActions";


type ChangePasswordDialogReducer = (state: ChangePasswordDialogState | undefined, action: ChangePasswordDialogAction) => ChangePasswordDialogState


const initialState: ChangePasswordDialogState = {
    user: null,
    showProgress: false
}

const changePasswordDialogReducer: ChangePasswordDialogReducer = (state = initialState, action) => {
    switch (action.type) {
        case "[CHANGE PASSWORD DIALOG] OPEN":
            return {
                user: action.user,
                showProgress: false
            };
        case "[CHANGE PASSWORD DIALOG] CLOSED":
            return {
                user: null,
                showProgress: false
            };
        case "[CHANGE PASSWORD DIALOG] SHOW PROGRESS":
            return {
                ...state,
                showProgress: true
            }
        case "[CHANGE PASSWORD DIALOG] HIDE PROGRESS":
            return {
                ...state,
                showProgress: false
            };
    }
    return state;
};

export default changePasswordDialogReducer;