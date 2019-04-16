import {combineReducers} from "redux";
import documentDialogReducer from "./DocumentDialogReducer";


const dialogReducer = combineReducers({
    documentDialog: documentDialogReducer
});

export default dialogReducer;