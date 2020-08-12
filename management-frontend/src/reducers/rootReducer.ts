import {combineReducers} from "redux";
import snackbarReducer from "./snackbarReducer";
import userDetailsReducer from "./userDetailsReducer";
import logsReducer from "./logsReducer";
import serversReducer from "./serversReducer";
import componentsReducer from "./componentsReducer";
import usersReducer from "./usersReducer";
import perfsReducer from "./perfsReducer";
import commandsReducer from "./commandsReducer";
import buildsReducer from "./buildsReducer";
import operationStatsReducer from "./operationStatsReducer";
import bugReportsReducer from "./bugReportReducer";
import corpusesReducer from "./corpusesReducer";

const reducers = {
    userDetails: userDetailsReducer,
    users: usersReducer,
    snackbar: snackbarReducer,
    logs: logsReducer,
    servers: serversReducer,
    components: componentsReducer,
    operationStats: operationStatsReducer,
    perfLogs: perfsReducer,
    commands: commandsReducer,
    builds: buildsReducer,
    bugReports: bugReportsReducer,
    corpuses: corpusesReducer
};


const rootReducer = combineReducers(
    reducers
);
export default rootReducer;