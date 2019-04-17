import {DocumentDialogAction} from "./DocumentDialogAction";
import {ContextDialogAction} from "./ContextDialogActions";
import {DeleteUserDialogAction} from "./DeleteUserDialogActions";


export type DialogAction = DocumentDialogAction | ContextDialogAction | DeleteUserDialogAction;