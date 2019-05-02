import {User} from "../entities/User";
import {ThunkResult} from "./RootActions";
import {mockChangePassword, mockDeleteUser, mockLoadUsers, mockUpdateUser} from "../mocks/mockUserApi";

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
    mockLoadUsers(dispatch)
};


export const updateUserAction = (user: User): ThunkResult<void> => (dispatch) => {
    mockUpdateUser(user, dispatch)
};

export const deleteUserAction = (user: User): ThunkResult<void> => dispatch => {
    mockDeleteUser(user, dispatch);
};

export const changePasswordAction = (user: User, newPassword: string): ThunkResult<void> => dispatch => {
    mockChangePassword(user, newPassword, dispatch);
};