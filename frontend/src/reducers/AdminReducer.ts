import {AdminState} from "./ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {User} from "../entities/User";
import {ThunkResult} from "../actions/RootActions";
import {API_BASE_PATH} from "../globals";
import {hideProgressbar, showProgressbar} from "./ProgressBarReducer";
import axios from "axios";
import {openSnackbar} from "./SnackBarReducer";
import {
    closeDeleteUserDialog,
    hideDeleteUserDialogProgress,
    showDeleteUserDialogProgress
} from "./dialog/DeleteUserDialogReducer";
import {
    closeChangePasswordDialog,
    hideChangePasswordDialogProgress,
    showChangePasswordDialogProgress
} from "./dialog/ChangePasswordDialogReducer";
import {parseValidationErrors} from "../actions/errors";


const {reducer, actions} = createSlice({
    slice: 'admin',
    initialState: {users: []} as AdminState,
    reducers: {
        adminLoadUsers: (state: AdminState, {payload}: PayloadAction<Array<User>>) => {
            state.users = payload
        },
        adminUpdateUser: (state: AdminState, {payload}: PayloadAction<User>) => {
            const userIndex = state.users.findIndex((user) => user.login == payload.login);
            if (userIndex == -1)
                throw `unknown user ${payload.login} , cannot update`;
            state.users[userIndex] = payload;
        },
        adminDeleteUser: (state: AdminState, {payload}: PayloadAction<User>) => {
            const userIndex = state.users.findIndex((user) => user.login == payload.login);
            if (userIndex == -1)
                throw `unknown user ${payload.login} , cannot delete`;
            state.users.splice(userIndex, 1)
        },
        adminCreateUser: (state: AdminState, {payload}: PayloadAction<User>) => {
            state.users.push(payload)
        }
    }
});

const {adminCreateUser, adminDeleteUser, adminLoadUsers, adminUpdateUser} = actions;
export default reducer;

export const adminLoadUserRequest = (): ThunkResult<void> => (dispatch) => {
    dispatch(showProgressbar());
    axios.get<Array<User>>(`${API_BASE_PATH}/user/all`, {withCredentials: true})
        .then(
            response => {
                dispatch(adminLoadUsers(response.data));
                dispatch(hideProgressbar());
            }
        )
        .catch(() => {
            dispatch(openSnackbar("Could not load users"))
            dispatch(hideProgressbar());
        })
};


export const adminUpdateUserRequest = (user: User): ThunkResult<void> => (dispatch) => {
    dispatch(showProgressbar());
    axios.put(`${API_BASE_PATH}/user`, user, {withCredentials: true})
        .then(() => {
            dispatch(openSnackbar(`User with login ${user.login} updated`));
            dispatch(adminUpdateUser(user));
            dispatch(hideProgressbar());
        })
        .catch(() => {
            dispatch(openSnackbar(`Failed to updated user ${user.login}`));
            dispatch(hideProgressbar());
        })
};

export const adminDeleteUserRequest = (user: User): ThunkResult<void> => dispatch => {
    dispatch(showDeleteUserDialogProgress());
    axios.delete(`${API_BASE_PATH}/user/${user.id}`, {withCredentials: true})
        .then(() => {
            dispatch(openSnackbar(`User with login ${user.login} deleted`));
            dispatch(adminDeleteUser(user));
            dispatch(hideDeleteUserDialogProgress());
            dispatch(closeDeleteUserDialog());
        })
        .catch(() => {
            dispatch(openSnackbar(`Could not delete user with ${user.login}`));
            dispatch(hideDeleteUserDialogProgress());
        })
};

export const adminChangePasswordRequest = (user: User, newPassword: string): ThunkResult<void> => dispatch => {
    dispatch(showChangePasswordDialogProgress());
    axios.put(`${API_BASE_PATH}/user/password`, {
        login: user.login,
        newPassword,
        oldPassword: 'NULL_PASSWORD'
    }, {withCredentials: true})
        .then(() => {
            dispatch(openSnackbar(`Changed password of user ${user.login}`));
            dispatch(hideChangePasswordDialogProgress());
            dispatch(closeChangePasswordDialog());
        })
        .catch(() => {
            dispatch(openSnackbar(`Failed to change password of ${user.login}`));
            dispatch(hideChangePasswordDialogProgress());
        });
};

export const adminCreateNewUserRequest = (login: string, password: string, roles: Array<string>, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => dispatch => {
    axios.post<User>(`${API_BASE_PATH}/user/add`, {
        login,
        password,
        roles
    }, {withCredentials: true})
        .then(response => {
            dispatch(openSnackbar(`Created user ${login}`));
            dispatch(adminCreateUser(response.data));
            onDone();
        })
        .catch((response) => {
            const errors = response.response.data.status === 400 ? parseValidationErrors(response) : {};
            onError(errors);
            dispatch(openSnackbar(`Failed to create user ${login}`));
        })
};