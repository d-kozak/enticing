import {Dispatch} from "redux";
import {openSnackBar} from "../actions/SnackBarActions";
import {loginSuccessAction} from "../actions/UserActions";
import {deleteUserSuccessAction, updateUserSuccessAction, usersLoadedAction} from "../actions/AdminActions";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {User} from "../entities/User";
import {
    deleteUserDialogClosedAction,
    deleteUserDialogHideProgressAction,
    deleteUserDialogShowProgressAction
} from "../actions/dialog/DeleteUserDialogActions";


interface MockUser {
    login: string,
    password: string,
    isAdmin: boolean,
    isActive: boolean
}

const mockUsers = new Map<string, MockUser>([
    [
        'user1', {
        login: 'user1',
        password: 'user1',
        isActive: true,
        isAdmin: false
    }
    ],
    [
        'passive', {
        login: 'passive',
        password: 'passive',
        isActive: false,
        isAdmin: false
    }
    ],
    [
        'admin', {
        login: 'admin',
        password: 'admin',
        isActive: true,
        isAdmin: true
    }
    ]
]);


export const mockSignup = (login: string, password: string, dispatch: Dispatch, onError: (error: any) => void) => {
    setTimeout(() => {
        const user = mockUsers.get(login);
        if (!user) {
            mockUsers.set(login, {
                login,
                password,
                isActive: true,
                isAdmin: false
            });
            dispatch(openSnackBar('Signed up successfully'))
            dispatch(loginSuccessAction(login, false))
        } else {
            onError({
                login: 'This login is already taken'
            });
        }
    }, 2000);
}

export const mockLogin = (login: string, password: string, dispatch: Dispatch, onError: (errors: any) => void) => {
    setTimeout(() => {
        const user = mockUsers.get(login);
        if (user) {
            if (!user.isActive) {
                onError({login: 'Your account is not active anymore'});
            } else if (user.password === password) {
                dispatch(openSnackBar('Logged in'));
                dispatch(loginSuccessAction(login, user.isAdmin));
            } else {
                onError({password: 'Invalid password'});
            }
        } else {
            onError({login: 'Unknown login'});
        }
    }, 2000);
}

export const mockLoadUsers = (dispatch: Dispatch) => {
    dispatch(showProgressBarAction());
    setTimeout(() => {
        const users = Array.from(mockUsers.values()).map(user => ({...user, password: ''}));
        dispatch(hideProgressBarAction());
        dispatch(usersLoadedAction(users));
    }, 2000);
}

export const mockUpdateUser = (user: User, dispatch: Dispatch) => {
    dispatch(showProgressBarAction());
    setTimeout(() => {
        const mockUser = mockUsers.get(user.login);
        if (mockUser) {
            mockUsers.set(user.login, {
                ...mockUser,
                ...user,
                password: mockUser.password
            });
            dispatch(openSnackBar(`User with login ${user.login} updated`));
            dispatch(updateUserSuccessAction(user));
        } else {
            dispatch(openSnackBar(`User with login ${user.login} does not exist`));
        }
        dispatch(hideProgressBarAction());
    }, 2000);
}


export const mockDeleteUser = (user: User, dispatch: Dispatch) => {
    dispatch(deleteUserDialogShowProgressAction());
    setTimeout(() => {
        if (mockUsers.delete(user.login)) {
            dispatch(openSnackBar(`User with login ${user.login} deleted`));
            dispatch(deleteUserSuccessAction(user))
        } else {
            dispatch(openSnackBar(`User with login ${user.login} does not exist, cannot be deleted`));
        }
        dispatch(deleteUserDialogHideProgressAction());
        dispatch(deleteUserDialogClosedAction());
    }, 2000);
};