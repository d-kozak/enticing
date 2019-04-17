import {combineReducers} from "redux";
import documentDialogReducer from "./DocumentDialogReducer";
import contextDialogReducer from "./ContextDialogReducer";
import deleteUserDialogReducer from "./DeleteUserDialogReducer";
import changePasswordDialogReducer from "./ChangePasswordDialogReducer";


const dialogReducer = combineReducers({
    documentDialog: documentDialogReducer,
    contextDialog: contextDialogReducer,
    deleteUserDialog: deleteUserDialogReducer,
    changePasswordDialog: changePasswordDialogReducer
});

export default dialogReducer;