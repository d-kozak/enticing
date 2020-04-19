import {ApplicationState} from "../ApplicationState";
import {ThunkAction} from "redux-thunk";
import {AnyAction} from "redux";

export type ThunkResult<R> = ThunkAction<R, ApplicationState, undefined, AnyAction>