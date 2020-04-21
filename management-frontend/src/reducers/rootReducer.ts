import {combineReducers} from "redux";
import snackbarReducer from "./snackbarReducer";
import userDetailsReducer from "./userDetailsReducer";
import logsReducer from "./logsReducer";
import serversReducer from "./serversReducer";
import componentsReducer from "./componentsReducer";
import usersReducer from "./usersReducer";
import perfsReducer from "./perfsReducer";
import operationStatsReducer from "./operationStatsReducer";

const reducers = {
    userDetails: userDetailsReducer,
    users: usersReducer,
    snackbar: snackbarReducer,
    logs: logsReducer,
    servers: serversReducer,
    components: componentsReducer,
    operationStats: operationStatsReducer,
    perfLogs: perfsReducer,
};


const rootReducer = combineReducers(
    reducers
);
export default rootReducer;