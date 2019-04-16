import {combineReducers} from "redux";
import documentDialogReducer from "./DocumentDialogReducer";
import contextDialogReducer from "./ContextDialogReducer";


const dialogReducer = combineReducers({
    documentDialog: documentDialogReducer,
    contextDialog: contextDialogReducer
});

export default dialogReducer;