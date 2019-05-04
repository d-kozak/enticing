import {combineReducers} from "redux";
import userReducer from "./UserReducer";
import searchResultReducer from "./SearchResultReducer";

import {RootAction} from "../actions/RootActions";
import snackBarReducer from './SnackBarReducer';
import progressBarReducer from './ProgressBarReducer';
import adminReducer from "./AdminReducer";
import dialogReducer from "./dialog/DialogReducer";
import {StateFromReducers} from "./StateFromReducers";
import userSettingsReducer from "./UserSettingsReducer";
import searchSettingsReducer from "./SearchSettingsReducer";

const reducers = {
    user: userReducer,
    searchResults: searchResultReducer,
    snackBar: snackBarReducer,
    progressBar: progressBarReducer,
    admin: adminReducer,
    userSettings: userSettingsReducer,
    searchSettings: searchSettingsReducer,
    dialog: dialogReducer
};

export type AppState = Readonly<StateFromReducers<typeof reducers>>

type RootReducer = (state: AppState | undefined, action: RootAction) => AppState

const rootReducer: RootReducer = combineReducers(
    reducers
)

export default rootReducer;
