import {combineReducers} from "redux";
import documentDialogReducer from "./DocumentDialogReducer";
import contextDialogReducer from "./ContextDialogReducer";
import deleteUserDialogReducer from "./DeleteUserDialogReducer";
import changePasswordDialogReducer from "./ChangePasswordDialogReducer";
import {ReducerState} from "../utils";


const reducers = {
    documentDialog: documentDialogReducer,
    contextDialog: contextDialogReducer,
    deleteUserDialog: deleteUserDialogReducer,
    changePasswordDialog: changePasswordDialogReducer
};

export type DialogState = Readonly<ReducerState<typeof reducers>>;

export default combineReducers(reducers);
