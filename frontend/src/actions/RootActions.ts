import {QueryAction} from "./QueryActions";
import {SearchResultAction} from "./SearchResultActions";
import {UserAction} from "./UserActions";
import {ThunkAction} from "redux-thunk";
import {AppState} from "../AppState";
import {AdminAction} from "./AdminActions";
import {DialogAction} from "./dialog/DialogActions";
import {SnackBarAction} from "./SnackBarActions";
import {ProgressBarAction} from "./ProgressBarActions";

export type RootAction =
    QueryAction
    | SearchResultAction
    | UserAction
    | AdminAction
    | DialogAction
    | SnackBarAction
    | ProgressBarAction

export type ThunkResult<R> = ThunkAction<R, AppState, undefined, RootAction>