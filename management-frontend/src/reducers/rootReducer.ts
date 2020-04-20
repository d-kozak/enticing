import {combineReducers} from "redux";
import snackbarReducer from "./snackbarReducer";
import userReducer from "./userReducer";
import logReducer from "./logReducer";

const reducers = {
    user: userReducer,
    snackbar: snackbarReducer,
    logs: logReducer
};


const rootReducer = combineReducers(
    reducers
);
export default rootReducer;