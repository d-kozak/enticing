import {DeleteUserDialogState} from "../../ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {User} from "../../entities/User";

const {reducer, actions} = createSlice({
    slice: 'deleteUserDialog',
    initialState: {
        userToDelete: null as User | null,
        showProgress: false
    } as DeleteUserDialogState,
    reducers: {
        openDeleteUserDialog: (state: DeleteUserDialogState, {payload}: PayloadAction<User>) => {
            state.userToDelete = payload;
            state.showProgress = false;
        },
        closeDeleteUserDialog: (state: DeleteUserDialogState) => {
            state.userToDelete = null;
            state.showProgress = false;
        },
        showDeleteUserDialogProgress: (state: DeleteUserDialogState) => {
            state.showProgress = true;
        },
        hideDeleteUserDialogProgress: (state: DeleteUserDialogState) => {
            state.showProgress = false;
        }
    }
});


export const {closeDeleteUserDialog, hideDeleteUserDialogProgress, openDeleteUserDialog, showDeleteUserDialogProgress} = actions;
export default reducer;