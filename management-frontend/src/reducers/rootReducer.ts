import {combineReducers} from "redux";
import snackbarReducer from "./snackbarReducer";
import userReducer from "./userReducer";
import logsReducer from "./logsReducer";
import serversReducer from "./serversReducer";

const reducers = {
    user: userReducer,
    snackbar: snackbarReducer,
    logs: logsReducer,
    servers: serversReducer
};


const rootReducer = combineReducers(
    reducers
);
export default rootReducer;