import {QueryAction} from "./QueryActions";
import {SearchResultAction} from "./SearchResultActions";
import {UserAction} from "./UserActions";
import {ThunkAction} from "redux-thunk";
import {AppState} from "../AppState";
import {AdminAction} from "./AdminActions";
import {DialogAction} from "./dialog/DialogAction";

export type RootAction = QueryAction | SearchResultAction | UserAction | AdminAction | DialogAction

export type ThunkResult<R> = ThunkAction<R, AppState, undefined, RootAction>