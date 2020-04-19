import {combineReducers} from "redux";
import snackbarReducer from "./snackbarReducer";
import userReducer from "./userReducer";

const reducers = {
    user: userReducer,
    snackbar: snackbarReducer
};


const rootReducer = combineReducers(
    reducers
);
export default rootReducer;