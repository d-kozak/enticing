import {Dispatch} from "redux";
import {openSnackBar} from "../actions/SnackBarActions";
import {
    loginSuccessAction,
    logoutSuccessAction,
    userSearchSettingsSelectedSuccessAction,
    userSettingsUpdatedAction
} from "../actions/UserActions";
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
import {loadSearchSettingsAction} from "../actions/SearchSettingsActions";
import {UserSettings as UserSettingsModel} from "../entities/UserSettings";
import {corpusFormatLoadedAction} from "../actions/CorpusFormatActions";
import {mockCorpusFormat} from "./mockSearchApi";


interface MockUser extends User {
    password: string,
}

const mockUsers = new Map<string, MockUser>([
    [
        'user1', {
        id: 0,
        login: 'user1',
        password: 'user1',
        active: true,
        roles: [],
        selectedSettings: null,
        selectedMetadata: null,
        userSettings: {
            resultsPerPage: 20
        }
    }
    ],
    [
        'passive', {
        id: 1,
        login: 'passive',
        password: 'passive',
        active: false,
        roles: [],
        selectedSettings: null,
        selectedMetadata: null,
        userSettings: {
            resultsPerPage: 25
        }
    }
    ],
    [
        'admin', {
        id: 2,
        login: 'admin',
        password: 'admin',
        active: true,
        roles: ['ADMIN'],
        selectedSettings: null,
        selectedMetadata: null,
        userSettings: {
            resultsPerPage: 50
        }
    }
    ]
]);

let counter = 3

export const mockSignup = (login: string, password: string, dispatch: Dispatch, onError: (error: any) => void) => {
    setTimeout(() => {
        const user = mockUsers.get(login);
        if (!user) {
            const newUser = {
                id: counter++,
                login,
                password,
                active: true,
                roles: [],
                selectedSettings: null,
                selectedMetadata: null,
                userSettings: {
                    resultsPerPage: 20
                }
            };
            mockUsers.set(login, newUser);
            dispatch(openSnackBar('Signed up successfully'));
            dispatch(loginSuccessAction(newUser))
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
            if (!user.active) {
                onError({login: 'Your account is not active anymore'});
            } else if (user.password === password) {
                dispatch(openSnackBar('Logged in'));

                // @ts-ignore
                dispatch(loadSearchSettingsAction(user));

                dispatch(loginSuccessAction(user));
            } else {
                onError({password: 'Invalid password'});
            }
        } else {
            onError({login: 'Unknown login'});
        }
    }, 2000);
}

export const mockLogout = (dispatch: Dispatch) => {
    dispatch(openSnackBar('Logged out'));
    // @ts-ignore
    dispatch(loadSearchSettingsAction(null));
    dispatch(logoutSuccessAction());
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


export const mockUpdateUserSettings = (settings: UserSettingsModel, onDone: () => void, dispatch: Dispatch) => {
    setTimeout(() => {
        dispatch(userSettingsUpdatedAction(settings));
        dispatch(openSnackBar("User settings updated"));
        onDone()
    }, 2000);
}

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
};

export const mockUserSettingsSelectedRequest = (settings: SearchSettings, dispatch: Dispatch) => {
    dispatch(showProgressBarAction());
    setTimeout(() => {
        dispatch(userSearchSettingsSelectedSuccessAction(settings));
        dispatch(corpusFormatLoadedAction(Number(settings.id), mockCorpusFormat));
        dispatch(openSnackBar(`Selected configuration ${settings.name}`));
        dispatch(hideProgressBarAction());
    }, 2000);
};