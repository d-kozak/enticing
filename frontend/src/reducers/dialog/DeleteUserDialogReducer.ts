import {DeleteUserDialogState} from "../../AppState";
import {DeleteUserDialogAction} from "../../actions/dialog/DeleteUserDialogActions";


type DeleteUserDialogReducer = (state: DeleteUserDialogState | undefined, action: DeleteUserDialogAction) => DeleteUserDialogState


const initialState: DeleteUserDialogState = {
    userToDelete: null,
    showProgress: false
}

const deleteUserDialogReducer: DeleteUserDialogReducer = (state = initialState, action) => {
    switch (action.type) {
        case "[DELETE USER DIALOG] OPEN":
            return {
                userToDelete: action.userToDelete,
                showProgress: false
            };
        case "[DELETE USER DIALOG] CLOSED":
            return {
                userToDelete: null,
                showProgress: false
            };

        case "[DELETE USER DIALOG] SHOW PROGRESS":
            return {
                ...state,
                showProgress: true
            };
        case "[DELETE USER DIALOG] HIDE PROGRESS":
            return {
                ...state,
                showProgress: false
            };

    }
    return state;
};

export default deleteUserDialogReducer;