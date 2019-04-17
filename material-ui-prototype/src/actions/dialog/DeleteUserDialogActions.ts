import {User} from "../../entities/User";

interface DeleteUserDialogOpenAction {
    type: '[DELETE USER DIALOG] OPEN',
    userToDelete: User
}

interface DeleteUserDialogClosedAction {
    type: '[DELETE USER DIALOG] CLOSED'
}

interface DeleteUserDialogShowProgressAction {
    type: '[DELETE USER DIALOG] SHOW PROGRESS'
}

interface DeleteUserDialogHideProgressAction {
    type: '[DELETE USER DIALOG] HIDE PROGRESS'
}

export type DeleteUserDialogAction =
    DeleteUserDialogOpenAction
    | DeleteUserDialogClosedAction
    | DeleteUserDialogShowProgressAction
    | DeleteUserDialogHideProgressAction

export const deleteUserDialogShowProgressAction = (): DeleteUserDialogShowProgressAction => ({
    type: "[DELETE USER DIALOG] SHOW PROGRESS"
});

export const deleteUserDialogHideProgressAction = (): DeleteUserDialogHideProgressAction => ({
    type: "[DELETE USER DIALOG] HIDE PROGRESS"
});

export const deleteUserDialogOpenAction = (userToDelete: User): DeleteUserDialogOpenAction => ({
    type: "[DELETE USER DIALOG] OPEN",
    userToDelete
});

export const deleteUserDialogClosedAction = (): DeleteUserDialogClosedAction => ({
    type: "[DELETE USER DIALOG] CLOSED"
});
