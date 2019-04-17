import {User} from "../entities/User";
import {ThunkResult} from "./RootAction";
import {mockDeleteUser, mockLoadUsers, mockUpdateUser} from "../mocks/mockUserApi";

interface UsersLoadedAction {
    type: '[ADMIN] USERS LOADED',
    users: Array<User>
}

interface UpdateUserSuccessAction {
    type: '[ADMIN] UPDATE USER SUCCESS'
    user: User
}

interface DeleteUserSuccessAction {
    type: '[ADMIN] DELETE USER SUCCESS',
    user: User
}

export type AdminAction = UsersLoadedAction | UpdateUserSuccessAction | DeleteUserSuccessAction

export const usersLoadedAction = (users: Array<User>): UsersLoadedAction => (
    {
        type: "[ADMIN] USERS LOADED",
        users
    }
);

export const updateUserSuccessAction = (user: User): UpdateUserSuccessAction => ({
    type: "[ADMIN] UPDATE USER SUCCESS",
    user
});


export const deleteUserSuccessAction = (user: User): DeleteUserSuccessAction => ({
    type: "[ADMIN] DELETE USER SUCCESS",
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