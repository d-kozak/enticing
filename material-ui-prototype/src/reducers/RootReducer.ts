import {combineReducers} from "redux";
import userReducer from "./UserReducer";
import searchResultReducer from "./SearchResultReducer";
import queryReducer from "./QueryReducer";
import {AppState} from "../AppState";
import {RootAction} from "../actions/RootAction";
import snackBarReducer from './SnackBarReducer';
import progressBarReducer from './ProgressBarReducer';
import adminReducer from "./AdminReducer";
import dialogReducer from "./dialog/DialogReducer";

type RootReducer = (state: AppState | undefined, action: RootAction) => AppState

const rootReducer: RootReducer = combineReducers(
    {
        user: userReducer,
        searchResults: searchResultReducer,
        query: queryReducer,
        snackBar: snackBarReducer,
        progressBar: progressBarReducer,
        admin: adminReducer,
        dialog: dialogReducer
    }
)

export default rootReducer;