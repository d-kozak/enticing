import {User} from "../../entities/User";

export const CHANGE_PASSWORD_DIALOG_OPEN = '[CHANGE PASSWORD DIALOG] OPEN';
export const CHANGE_PASSWORD_DIALOG_CLOSE = '[CHANGE PASSWORD DIALOG] CLOSED';
export const CHANGE_PASSWORD_DIALOG_SHOW_PROGRESS = '[CHANGE PASSWORD DIALOG] SHOW PROGRESS';
export const CHANGE_PASSWORD_DIALOG_HIDE_PROGRESS = '[CHANGE PASSWORD DIALOG] HIDE PROGRESS';

interface ChangePasswordDialogOpenAction {
    type: typeof CHANGE_PASSWORD_DIALOG_OPEN,
    user: User
}

interface ChangePasswordDialogClosedAction {
    type: typeof CHANGE_PASSWORD_DIALOG_CLOSE
}

interface ChangePasswordDialogShowProgressAction {
    type: typeof CHANGE_PASSWORD_DIALOG_SHOW_PROGRESS
}

interface ChangePasswordDialogHideProgressAction {
    type: typeof CHANGE_PASSWORD_DIALOG_HIDE_PROGRESS
}

export type ChangePasswordDialogAction =
    ChangePasswordDialogOpenAction
    | ChangePasswordDialogClosedAction
    | ChangePasswordDialogShowProgressAction
    | ChangePasswordDialogHideProgressAction

export const changePasswordDialogShowProgressAction = (): ChangePasswordDialogShowProgressAction => ({
    type: CHANGE_PASSWORD_DIALOG_SHOW_PROGRESS
});

export const changePasswordDialogHideProgressAction = (): ChangePasswordDialogHideProgressAction => ({
    type: CHANGE_PASSWORD_DIALOG_HIDE_PROGRESS
});

export const changePasswordDialogOpenAction = (user: User): ChangePasswordDialogOpenAction => ({
    type: CHANGE_PASSWORD_DIALOG_OPEN,
    user
});

export const changePasswordDialogClosedAction = (): ChangePasswordDialogClosedAction => ({
    type: CHANGE_PASSWORD_DIALOG_CLOSE
});
