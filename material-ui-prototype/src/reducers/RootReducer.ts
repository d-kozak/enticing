import {combineReducers} from "redux";
import userReducer from "./UserReducer";
import searchResultReducer from "./SearchResultReducer";
import queryReducer from "./QueryReducer";
import {AppState} from "../AppState";
import {RootAction} from "../actions/RootAction";
import snackBarReducer from './SnackBarReducer';

type RootReducer = (state: AppState | undefined, action: RootAction) => AppState

const rootReducer: RootReducer = combineReducers(
    {
        user: userReducer,
        searchResults: searchResultReducer,
        query: queryReducer,
        snackBar: snackBarReducer
    }
)

export default rootReducer;