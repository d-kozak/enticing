import {DocumentDialogAction} from "./DocumentDialogAction";
import {ContextDialogAction} from "./ContextDialogActions";
import {DeleteUserDialogAction} from "./DeleteUserDialogActions";
import {ChangePasswordDialogAction} from "./ChangePasswordDialogActions";


export type DialogAction =
    DocumentDialogAction
    | ContextDialogAction
    | DeleteUserDialogAction
    | ChangePasswordDialogAction;