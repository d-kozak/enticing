import {combineReducers} from "redux";
import snackbarReducer from "./snackbarReducer";
import userDetailsReducer from "./userDetailsReducer";
import logsReducer from "./logsReducer";
import serversReducer from "./serversReducer";
import componentsReducer from "./componentsReducer";
import usersReducer from "./usersReducer";

const reducers = {
    userDetails: userDetailsReducer,
    users: usersReducer,
    snackbar: snackbarReducer,
    logs: logsReducer,
    servers: serversReducer,
    components: componentsReducer,
};


const rootReducer = combineReducers(
    reducers
);
export default rootReducer;