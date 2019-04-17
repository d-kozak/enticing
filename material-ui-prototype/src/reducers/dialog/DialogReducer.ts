import {combineReducers} from "redux";
import documentDialogReducer from "./DocumentDialogReducer";
import contextDialogReducer from "./ContextDialogReducer";
import deleteUserDialogReducer from "./DeleteUserDialogReducer";


const dialogReducer = combineReducers({
    documentDialog: documentDialogReducer,
    contextDialog: contextDialogReducer,
    deleteUserDialog: deleteUserDialogReducer
});

export default dialogReducer;