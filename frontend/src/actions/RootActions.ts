import {ThunkAction} from "redux-thunk";
import {ApplicationState} from "../reducers/ApplicationState";
import {AnyAction} from "redux";

export type ThunkResult<R> = ThunkAction<R, ApplicationState, undefined, AnyAction>