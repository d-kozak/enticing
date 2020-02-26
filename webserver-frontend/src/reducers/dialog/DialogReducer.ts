import {combineReducers} from "redux";
import documentDialogReducer from "./DocumentDialogReducer";
import deleteUserDialogReducer from "./DeleteUserDialogReducer";
import changePasswordDialogReducer from "./ChangePasswordDialogReducer";
import rawDocumentDialogReducer from "./RawDocumentDialogReducer";

const reducers = {
    documentDialog: documentDialogReducer,
    deleteUserDialog: deleteUserDialogReducer,
    changePasswordDialog: changePasswordDialogReducer,
    rawDocumentDialog: rawDocumentDialogReducer
};

export default combineReducers(reducers);
