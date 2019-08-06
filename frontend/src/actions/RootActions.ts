import {SearchResultAction} from "./SearchResultActions";
import {UserAction} from "./UserActions";
import {ThunkAction} from "redux-thunk";
import {AdminAction} from "./AdminActions";
import {DialogAction} from "./dialog/DialogActions";
import {SnackBarAction} from "./SnackBarActions";
import {ProgressBarAction} from "./ProgressBarActions";
import {SearchSettingsAction} from "./SearchSettingsActions";
import {ApplicationState} from "../reducers/ApplicationState";

export type RootAction =
    SearchResultAction
    | UserAction
    | AdminAction
    | DialogAction
    | SnackBarAction
    | ProgressBarAction
    | SearchSettingsAction;

export type ThunkResult<R> = ThunkAction<R, ApplicationState, undefined, RootAction>