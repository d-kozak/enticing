import {SearchResultAction} from "./SearchResultActions";
import {UserAction} from "./UserActions";
import {ThunkAction} from "redux-thunk";
import {AppState} from "../reducers/RootReducer";
import {AdminAction} from "./AdminActions";
import {DialogAction} from "./dialog/DialogActions";
import {SnackBarAction} from "./SnackBarActions";
import {ProgressBarAction} from "./ProgressBarActions";

export type RootAction =
    SearchResultAction
    | UserAction
    | AdminAction
    | DialogAction
    | SnackBarAction
    | ProgressBarAction

export type ThunkResult<R> = ThunkAction<R, AppState, undefined, RootAction>