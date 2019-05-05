import {Dispatch} from "redux";
import {openSnackBar} from "../actions/SnackBarActions";
import {loginSuccessAction, userSearchSettingsSelectedSuccessAction} from "../actions/UserActions";
import {deleteUserSuccessAction, updateUserSuccessAction, usersLoadedAction} from "../actions/AdminActions";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {User} from "../entities/User";
import {
    deleteUserDialogClosedAction,
    deleteUserDialogHideProgressAction,
    deleteUserDialogShowProgressAction
} from "../actions/dialog/DeleteUserDialogActions";
import {
    changePasswordDialogClosedAction,
    changePasswordDialogHideProgressAction,
    changePasswordDialogShowProgressAction
} from "../actions/dialog/ChangePasswordDialogActions";
import {SearchSettings} from "../entities/SearchSettings";
import {userSettingsLoadedAction, userSettingsUpdatedAction} from "../actions/UserSettingsActions";
import {UserSettings} from "../entities/UserSettings";


interface MockUser extends User {
    password: string,
}

const mockUsers = new Map<string, MockUser>([
    [
        'user1', {
        id: 0,
        login: 'user1',
        password: 'user1',
        isActive: true,
        isAdmin: false
    }
    ],
    [
        'passive', {
        id: 1,
        login: 'passive',
        password: 'passive',
        isActive: false,
        isAdmin: false
    }
    ],
    [
        'admin', {
        id: 2,
        login: 'admin',
        password: 'admin',
        isActive: true,
        isAdmin: true
    }
    ]
]);

let counter = 3

export const mockSignup = (login: string, password: string, dispatch: Dispatch, onError: (error: any) => void) => {
    setTimeout(() => {
        const user = mockUsers.get(login);
        if (!user) {
            mockUsers.set(login, {
                id: counter++,
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


export const mockChangePassword = (user: User, newPassword: string, dispatch: Dispatch) => {
    dispatch(changePasswordDialogShowProgressAction());
    setTimeout(() => {
        const userToChange = mockUsers.get(user.login);
        if (userToChange) {
            userToChange.password = newPassword;
            dispatch(openSnackBar(`Changed password of user ${user.login}`));
        } else {
            dispatch(openSnackBar(`User with login ${user.login} does not exist, cannot change his password`));
        }

        dispatch(changePasswordDialogHideProgressAction());
        dispatch(changePasswordDialogClosedAction());
    }, 2000);
}

export const mockUserSettingsSelectedRequest = (settings: SearchSettings, dispatch: Dispatch) => {
    dispatch(showProgressBarAction());
    setTimeout(() => {
        dispatch(userSearchSettingsSelectedSuccessAction(settings));
        dispatch(openSnackBar(`Selected configuration ${settings.name}`));
        dispatch(hideProgressBarAction());
    }, 2000);
};

export const mockUserSettingsRequest = (dispatch: Dispatch) => {
    dispatch(showProgressBarAction());
    setTimeout(() => {
        dispatch(userSettingsLoadedAction({
            resultsPerPage: 42
        }));
        dispatch(hideProgressBarAction());
    }, 2000);
};

export const mockUserSettingsUpdate = (dispatch: Dispatch, settings: UserSettings, oneDone: () => void) => {
    dispatch(showProgressBarAction());
    setTimeout(() => {
        dispatch(userSettingsUpdatedAction(settings));
        oneDone();
        dispatch(openSnackBar('User settings updated'));
        dispatch(hideProgressBarAction());
    }, 2000);
};