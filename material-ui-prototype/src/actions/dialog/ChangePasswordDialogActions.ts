import {User} from "../../entities/User";

interface ChangePasswordDialogOpenAction {
    type: '[CHANGE PASSWORD DIALOG] OPEN',
    user: User
}

interface ChangePasswordDialogClosedAction {
    type: '[CHANGE PASSWORD DIALOG] CLOSED'
}

interface ChangePasswordDialogShowProgressAction {
    type: '[CHANGE PASSWORD DIALOG] SHOW PROGRESS'
}

interface ChangePasswordDialogHideProgressAction {
    type: '[CHANGE PASSWORD DIALOG] HIDE PROGRESS'
}

export type ChangePasswordDialogAction =
    ChangePasswordDialogOpenAction
    | ChangePasswordDialogClosedAction
    | ChangePasswordDialogShowProgressAction
    | ChangePasswordDialogHideProgressAction

export const changePasswordDialogShowProgressAction = (): ChangePasswordDialogShowProgressAction => ({
    type: "[CHANGE PASSWORD DIALOG] SHOW PROGRESS"
});

export const changePasswordDialogHideProgressAction = (): ChangePasswordDialogHideProgressAction => ({
    type: "[CHANGE PASSWORD DIALOG] HIDE PROGRESS"
});

export const changePasswordDialogOpenAction = (user: User): ChangePasswordDialogOpenAction => ({
    type: "[CHANGE PASSWORD DIALOG] OPEN",
    user
});

export const changePasswordDialogClosedAction = (): ChangePasswordDialogClosedAction => ({
    type: "[CHANGE PASSWORD DIALOG] CLOSED"
});
