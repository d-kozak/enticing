import {User} from "../entities/User";
import {ThunkResult} from "./RootActions";
import {mockChangePassword, mockDeleteUser, mockLoadUsers, mockUpdateUser} from "../mocks/mockUserApi";
import {API_BASE_PATH, useMockApi} from "../globals";
import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "./ProgressBarActions";
import {openSnackBar} from "./SnackBarActions";
import {
    changePasswordDialogClosedAction,
    changePasswordDialogHideProgressAction,
    changePasswordDialogShowProgressAction
} from "./dialog/ChangePasswordDialogActions";
import {
    deleteUserDialogClosedAction,
    deleteUserDialogHideProgressAction,
    deleteUserDialogShowProgressAction
} from "./dialog/DeleteUserDialogActions";

export const ADMIN_USERS_LOADED = '[ADMIN] USERS LOADED';
export const ADMIN_USER_UPDATE_SUCCESS = '[ADMIN] UPDATE USER SUCCESS';
export const ADMIN_DELETE_USER_SUCCESS = '[ADMIN] DELETE USER SUCCESS';

interface UsersLoadedAction {
    type: typeof ADMIN_USERS_LOADED,
    users: Array<User>
}

interface UpdateUserSuccessAction {
    type: typeof ADMIN_USER_UPDATE_SUCCESS
    user: User
}

interface DeleteUserSuccessAction {
    type: typeof ADMIN_DELETE_USER_SUCCESS,
    user: User
}

export type AdminAction = UsersLoadedAction | UpdateUserSuccessAction | DeleteUserSuccessAction

export const usersLoadedAction = (users: Array<User>): UsersLoadedAction => ({
    type: ADMIN_USERS_LOADED,
    users
});

export const updateUserSuccessAction = (user: User): UpdateUserSuccessAction => ({
    type: ADMIN_USER_UPDATE_SUCCESS,
    user
});

export const deleteUserSuccessAction = (user: User): DeleteUserSuccessAction => ({
    type: ADMIN_DELETE_USER_SUCCESS,
    user
});

export const loadUsersAction = (): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockLoadUsers(dispatch)
        return;
    }
    dispatch(showProgressBarAction());
    axios.get<Array<User>>(`${API_BASE_PATH}/user/all`, {withCredentials: true})
        .then(
            response => {
                dispatch(usersLoadedAction(response.data));
                dispatch(hideProgressBarAction());
            }
        )
        .catch(() => {
            dispatch(openSnackBar('Could not load users'))
            dispatch(hideProgressBarAction());
        })
};


export const updateUserAction = (user: User): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockUpdateUser(user, dispatch)
        return;
    }
    dispatch(showProgressBarAction());
    axios.put(`${API_BASE_PATH}/user`, user, {withCredentials: true})
        .then(() => {
            dispatch(openSnackBar(`User with login ${user.login} updated`));
            dispatch(updateUserSuccessAction(user));
            dispatch(hideProgressBarAction());
        })
        .catch(() => {
            dispatch(openSnackBar(`Failed to updated user${user.login}`));
            dispatch(hideProgressBarAction());
        })
};

export const deleteUserAction = (user: User): ThunkResult<void> => dispatch => {
    if (useMockApi()) {
        mockDeleteUser(user, dispatch);
        return;
    }
    dispatch(deleteUserDialogShowProgressAction());
    axios.delete(`${API_BASE_PATH}/user/${user.id}`, {withCredentials: true})
        .then(() => {
            dispatch(openSnackBar(`User with login ${user.login} deleted`));
            dispatch(deleteUserSuccessAction(user))
            dispatch(deleteUserDialogHideProgressAction());
            dispatch(deleteUserDialogClosedAction());
        })
        .catch(() => {
            dispatch(openSnackBar(`Could not delete user with ${user.login}`));
            dispatch(deleteUserDialogHideProgressAction());
        })
};

export const changePasswordAction = (user: User, newPassword: string): ThunkResult<void> => dispatch => {
    if (useMockApi()) {
        mockChangePassword(user, newPassword, dispatch);
        return;
    }
    dispatch(changePasswordDialogShowProgressAction());
    axios.put(`${API_BASE_PATH}/user/password`, {
        login: user.login,
        newPassword,
        oldPassword: ''
    }, {withCredentials: true})
        .then(() => {
            dispatch(openSnackBar(`Changed password of user ${user.login}`));
            dispatch(changePasswordDialogHideProgressAction());
            dispatch(changePasswordDialogClosedAction());
        })
        .catch(() => {
            dispatch(openSnackBar(`Failed to change password of ${user.login}`));
            dispatch(changePasswordDialogHideProgressAction());
        });
};