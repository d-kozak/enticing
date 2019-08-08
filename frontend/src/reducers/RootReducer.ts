import {combineReducers} from "redux";
import userReducer from "./UserReducer";
import searchResultReducer from "./SearchResultReducer";
import snackBarReducer from './SnackBarReducer';
import progressBarReducer from './ProgressBarReducer';
import adminReducer from "./AdminReducer";
import dialogReducer from "./dialog/DialogReducer";
import searchSettingsReducer from "./SearchSettingsReducer"

const reducers = {
    userState: userReducer,
    searchResult: searchResultReducer,
    snackBar: snackBarReducer,
    progressBar: progressBarReducer,
    adminState: adminReducer,
    searchSettings: searchSettingsReducer,
    dialog: dialogReducer
};


const rootReducer = combineReducers(
    reducers
);
export default rootReducer;



