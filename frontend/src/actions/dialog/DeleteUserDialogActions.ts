import {User} from "../../entities/User";

export const DELETE_USER_DIALOG_OPEN = '[DELETE USER DIALOG] OPEN';
export const DELETE_USER_DIALOG_CLOSED = '[DELETE USER DIALOG] CLOSED';
export const DELETE_USER_DIALOG_SHOW_PROGRESS = '[DELETE USER DIALOG] SHOW PROGRESS';
export const DELETE_USER_DIALOG_HIDE_PROGRESS = '[DELETE USER DIALOG] HIDE PROGRESS';

interface DeleteUserDialogOpenAction {
    type: typeof DELETE_USER_DIALOG_OPEN,
    userToDelete: User
}

interface DeleteUserDialogClosedAction {
    type: typeof DELETE_USER_DIALOG_CLOSED
}

interface DeleteUserDialogShowProgressAction {
    type: typeof DELETE_USER_DIALOG_SHOW_PROGRESS
}

interface DeleteUserDialogHideProgressAction {
    type: typeof DELETE_USER_DIALOG_HIDE_PROGRESS
}

export type DeleteUserDialogAction =
    DeleteUserDialogOpenAction
    | DeleteUserDialogClosedAction
    | DeleteUserDialogShowProgressAction
    | DeleteUserDialogHideProgressAction

export const deleteUserDialogShowProgressAction = (): DeleteUserDialogShowProgressAction => ({
    type: DELETE_USER_DIALOG_SHOW_PROGRESS
});

export const deleteUserDialogHideProgressAction = (): DeleteUserDialogHideProgressAction => ({
    type: DELETE_USER_DIALOG_HIDE_PROGRESS
});

export const deleteUserDialogOpenAction = (userToDelete: User): DeleteUserDialogOpenAction => ({
    type: DELETE_USER_DIALOG_OPEN,
    userToDelete
});

export const deleteUserDialogClosedAction = (): DeleteUserDialogClosedAction => ({
    type: DELETE_USER_DIALOG_CLOSED
});
