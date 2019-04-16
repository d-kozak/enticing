import {User} from "../entities/User";
import {ThunkResult} from "./RootAction";
import {mockLoadUsers, mockUpdateUser} from "../mocks/mockUserApi";

interface UsersLoadedAction {
    type: '[ADMIN] USERS LOADED',
    users: Array<User>
}

interface UpdateUserSuccessAction {
    type: '[ADMIN] UPDATE USER SUCCESS'
    user: User
}

export type AdminAction = UsersLoadedAction | UpdateUserSuccessAction

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


export const loadUsersAction = (): ThunkResult<void> => (dispatch) => {
    mockLoadUsers(dispatch)
};


export const updateUserAction = (user: User): ThunkResult<void> => (dispatch) => {
    mockUpdateUser(user, dispatch)
};

