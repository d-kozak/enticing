import {QueryAction} from "./QueryActions";
import {SearchResultAction} from "./SearchResultActions";
import {UserAction} from "./UserActions";
import {ThunkAction} from "redux-thunk";
import {AppState} from "../AppState";

export type RootAction = QueryAction | SearchResultAction | UserAction

export type ThunkResult<R> = ThunkAction<R, AppState, undefined, RootAction>