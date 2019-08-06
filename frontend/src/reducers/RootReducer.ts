import {combineReducers} from "redux";
import userReducer from "./UserReducer";
import searchResultReducer from "./SearchResultReducer";

import {RootAction} from "../actions/RootActions";
import snackBarReducer from './SnackBarReducer';
import progressBarReducer from './ProgressBarReducer';
import adminReducer from "./AdminReducer";
import dialogReducer from "./dialog/DialogReducer";
import searchSettingsReducer from "./SearchSettingsReducer";
import {ApplicationState} from "./ApplicationState";

const reducers = {
    userState: userReducer,
    searchResult: searchResultReducer,
    snackBar: snackBarReducer,
    progressBar: progressBarReducer,
    adminState: adminReducer,
    searchSettings: searchSettingsReducer,
    dialog: dialogReducer
};


type RootReducer = (state: ApplicationState | undefined, action: RootAction) => ApplicationState

const rootReducer: RootReducer = combineReducers(
    reducers
)

export default rootReducer;



