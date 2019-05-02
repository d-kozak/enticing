import {combineReducers} from "redux";
import userReducer from "./UserReducer";
import searchResultReducer from "./SearchResultReducer";
import queryReducer from "./QueryReducer";

import {RootAction} from "../actions/RootActions";
import snackBarReducer from './SnackBarReducer';
import progressBarReducer from './ProgressBarReducer';
import adminReducer from "./AdminReducer";
import dialogReducer from "./dialog/DialogReducer";
import {StateFromReducers} from "./StateFromReducers";

const reducers = {
    user: userReducer,
    searchResults: searchResultReducer,
    query: queryReducer,
    snackBar: snackBarReducer,
    progressBar: progressBarReducer,
    admin: adminReducer,
    dialog: dialogReducer
};

export type AppState = Readonly<StateFromReducers<typeof reducers>>

type RootReducer = (state: AppState | undefined, action: RootAction) => AppState

const rootReducer: RootReducer = combineReducers(
    reducers
)

export default rootReducer;
