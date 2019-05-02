import {
    DELETE_USER_DIALOG_CLOSED,
    DELETE_USER_DIALOG_HIDE_PROGRESS,
    DELETE_USER_DIALOG_OPEN,
    DELETE_USER_DIALOG_SHOW_PROGRESS,
    DeleteUserDialogAction
} from "../../actions/dialog/DeleteUserDialogActions";
import {User} from "../../entities/User";


export type DeleteUserDialogState = Readonly<{
    userToDelete: User | null;
    showProgress: boolean;
}>

const initialState: DeleteUserDialogState = {
    userToDelete: null,
    showProgress: false
}

type DeleteUserDialogReducer = (state: DeleteUserDialogState | undefined, action: DeleteUserDialogAction) => DeleteUserDialogState


const deleteUserDialogReducer: DeleteUserDialogReducer = (state = initialState, action) => {
    switch (action.type) {
        case DELETE_USER_DIALOG_OPEN:
            return {
                userToDelete: action.userToDelete,
                showProgress: false
            };
        case DELETE_USER_DIALOG_CLOSED:
            return {
                userToDelete: null,
                showProgress: false
            };

        case DELETE_USER_DIALOG_SHOW_PROGRESS:
            return {
                ...state,
                showProgress: true
            };
        case DELETE_USER_DIALOG_HIDE_PROGRESS:
            return {
                ...state,
                showProgress: false
            };

    }
    return state;
};

export default deleteUserDialogReducer;