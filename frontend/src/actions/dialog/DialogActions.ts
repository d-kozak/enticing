import {DocumentDialogAction} from "./DocumentDialogAction";
import {DeleteUserDialogAction} from "./DeleteUserDialogActions";
import {ChangePasswordDialogAction} from "./ChangePasswordDialogActions";


export type DialogAction =
    DocumentDialogAction
    | DeleteUserDialogAction
    | ChangePasswordDialogAction;