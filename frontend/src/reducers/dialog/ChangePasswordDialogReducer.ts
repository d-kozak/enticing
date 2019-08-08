import {ChangePasswordDialogState} from "../ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {User} from "../../entities/User";

const {reducer, actions} = createSlice({
    slice: 'changePasswordDialog',
    initialState: {
        user: null,
        showProgress: false
    } as ChangePasswordDialogState,
    reducers: {
        openChangePasswordDialog: (state: ChangePasswordDialogState, {payload}: PayloadAction<User>) => {
            state.user = payload;
            state.showProgress = false;
        },
        closeChangePasswordDialog: (state: ChangePasswordDialogState) => {
            state.user = null;
            state.showProgress = false;
        },
        showChangePasswordDialogProgress: (state: ChangePasswordDialogState) => {
            state.showProgress = true;
        },
        hideChangePasswordDialogProgress: (state: ChangePasswordDialogState) => {
            state.showProgress = false;
        }
    }
});


export const {openChangePasswordDialog, closeChangePasswordDialog, showChangePasswordDialogProgress, hideChangePasswordDialogProgress} = actions;
export default reducer;
