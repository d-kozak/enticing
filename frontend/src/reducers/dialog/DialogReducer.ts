import {combineReducers} from "redux";
import documentDialogReducer from "./DocumentDialogReducer";
import contextDialogReducer from "./ContextDialogReducer";
import deleteUserDialogReducer from "./DeleteUserDialogReducer";
import changePasswordDialogReducer from "./ChangePasswordDialogReducer";

const reducers = {
    documentDialog: documentDialogReducer,
    contextDialog: contextDialogReducer,
    deleteUserDialog: deleteUserDialogReducer,
    changePasswordDialog: changePasswordDialogReducer
};

export default combineReducers(reducers);
