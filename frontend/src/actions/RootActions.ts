import {SearchResultAction} from "./SearchResultActions";
import {UserAction} from "./UserActions";
import {ThunkAction} from "redux-thunk";
import {AdminAction} from "./AdminActions";
import {DialogAction} from "./dialog/DialogActions";
import {ProgressBarAction} from "./ProgressBarActions";
import {SearchSettingsAction} from "./SearchSettingsActions";
import {ApplicationState} from "../reducers/ApplicationState";
import {AnyAction} from "redux";

export type RootAction =
    SearchResultAction
    | UserAction
    | AdminAction
    | DialogAction
    | ProgressBarAction
    | SearchSettingsAction
    ;

export type ThunkResult<R> = ThunkAction<R, ApplicationState, undefined, AnyAction>