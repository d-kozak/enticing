import {combineReducers} from "redux";
import documentDialogReducer from "./DocumentDialogReducer";
import deleteUserDialogReducer from "./DeleteUserDialogReducer";
import changePasswordDialogReducer from "./ChangePasswordDialogReducer";

const reducers = {
    documentDialog: documentDialogReducer,
    deleteUserDialog: deleteUserDialogReducer,
    changePasswordDialog: changePasswordDialogReducer
};

export default combineReducers(reducers);
