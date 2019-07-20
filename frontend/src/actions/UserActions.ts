import {ThunkResult} from "./RootActions";
import {
    mockChangePassword,
    mockLogin,
    mockLogout,
    mockSignup,
    mockUpdateUserSettings,
    mockUserSettingsSelectedRequest
} from "../mocks/mockUserApi";
import {SearchSettings} from "../entities/SearchSettings";
import {User} from "../entities/User";
import axios from "axios";
import {API_BASE_PATH, useMockApi} from "../globals";
import {openSnackBar} from "./SnackBarActions";
import {loadSearchSettingsAction} from "./SearchSettingsActions";
import {UserSettings} from "../entities/UserSettings";
import {hideProgressBarAction, showProgressBarAction} from "./ProgressBarActions";
import {
    changePasswordDialogClosedAction,
    changePasswordDialogHideProgressAction,
    changePasswordDialogShowProgressAction
} from "./dialog/ChangePasswordDialogActions";
import {parseValidationErrors} from "./errors";
import {isCorpusFormat} from "../entities/CorpusFormat";
import {corpusFormatLoadedAction} from "./CorpusFormatActions";

export const USER_LOGOUT = "[USER] LOGOUT";
export const USER_LOGIN_SUCCESS = "[USER] LOGIN SUCCESS";
export const USER_SETTINGS_UPDATED = "[USER] SETTINGS UPDATED";
export const USER_SEARCH_SETTINGS_SELECTED_SUCCESS = "[USER] SETTINGS SELECTED SUCCESS";

interface LogoutAction {
    type: typeof USER_LOGOUT
}

interface LoginSuccessAction {
    type: typeof USER_LOGIN_SUCCESS,
    user: User
}

interface UserSettingsUpdatedAction {
    type: typeof USER_SETTINGS_UPDATED,
    userSettings: UserSettings
}

interface UserSearchSettingsSelectedSuccessAction {
    type: typeof USER_SEARCH_SETTINGS_SELECTED_SUCCESS,
    settings: SearchSettings | string
}

export type UserAction =
    LoginSuccessAction
    | LogoutAction
    | UserSettingsUpdatedAction
    | UserSearchSettingsSelectedSuccessAction;

export const logoutSuccessAction = (): LogoutAction => ({type: USER_LOGOUT});

export const userSearchSettingsSelectedSuccessAction = (settings: SearchSettings | string): UserSearchSettingsSelectedSuccessAction => ({
    type: USER_SEARCH_SETTINGS_SELECTED_SUCCESS,
    settings
});

export const userSettingsUpdatedAction = (userSettings: UserSettings): UserSettingsUpdatedAction => ({
    type: USER_SETTINGS_UPDATED,
    userSettings
})

export const logoutRequestAction = (): ThunkResult<void> => dispatch => {
    if (useMockApi()) {
        mockLogout(dispatch);
        return
    }
    dispatch(showProgressBarAction());
    axios.get(`${API_BASE_PATH}/logout`)
        .then(() => {
            dispatch(openSnackBar('Logged out'));
            // @ts-ignore
            dispatch(loadSearchSettingsAction(null));
            dispatch(logoutSuccessAction());
            dispatch(hideProgressBarAction());
        })
        .catch(error => {
            dispatch(openSnackBar('Could not logout'));
            dispatch(hideProgressBarAction());
        });
};

export const loginSuccessAction = (user: User): LoginSuccessAction => ({
    type: USER_LOGIN_SUCCESS,
    user
});

export const searchSettingsSelectedRequestAction = (settings: SearchSettings, previousSelectedSettings: string, isLoggedIn: boolean): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockUserSettingsSelectedRequest(settings, dispatch);
        return;
    }
    dispatch(userSearchSettingsSelectedSuccessAction(settings));
    if (isLoggedIn) {
        axios.get(`${API_BASE_PATH}/search-settings/select/${settings.id}`, {withCredentials: true})
            .then(() => {
                return axios.get(`${API_BASE_PATH}/query/format/${settings.id}`, {withCredentials: true})
            })
            .then(response => {
                if (isCorpusFormat(response.data)) {
                    dispatch(corpusFormatLoadedAction(Number(settings.id), response.data))
                } else {
                    throw "cannot parse";
                }
            }).catch((error) => {
            console.error(error);
            dispatch(openSnackBar(`Failed to select settings ${settings.name}`));
            // rollback to previously selected
            dispatch(userSearchSettingsSelectedSuccessAction(previousSelectedSettings));
        })
    } else {
        // if not logged in, just load corpus format
        axios.get(`${API_BASE_PATH}/query/format/${settings.id}`, {withCredentials: true})
            .then(response => {
                if (isCorpusFormat(response.data)) {
                    dispatch(corpusFormatLoadedAction(Number(settings.id), response.data))
                } else {
                    throw "cannot parse";
                }
                dispatch(userSearchSettingsSelectedSuccessAction(settings));
            })
            .catch((error) => {
                console.error(error);
                dispatch(openSnackBar(`Failed to load corpus format for settings ${settings.name}`));
            })
    }
};

export const loginRequestAction = (login: string, password: string, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockLogin(login, password, dispatch, onError);
        return;
    }
    const formData = new FormData()
    formData.set("username", login)
    formData.set("password", password)

    axios.post(`${API_BASE_PATH}/login`, formData, {withCredentials: true})
        .then(response => {
            const user = response.data
            dispatch(loginSuccessAction(user));

            dispatch(openSnackBar('Logged in'));
            // @ts-ignore
            dispatch(loadSearchSettingsAction(user.roles.indexOf("ADMIN") != -1));
        })
        .catch(error => {
            if (error.response.data.status === 401) {
                onError({login: 'Invalid login or password'});
            } else {
                dispatch(openSnackBar('Could not log in'));
                onError({});
            }
        });
};


export const signUpAction = (login: string, password: string, onError: (error: any) => void): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockSignup(login, password, dispatch, onError);
        return;
    }
    axios.post(`${API_BASE_PATH}/user`, {
        login, password
    }).then(() => {
        dispatch(loginRequestAction(login, password, () => {
            dispatch(openSnackBar('Signup was successful, but subsequent login failed, please try to log in manually'))
        }))
        }
    ).catch(error => {
        if (error.response.data.status === 400) {
            onError(parseValidationErrors(error));
        } else {
            dispatch(openSnackBar('Could not log in'));
            onError({});
        }
        }
    )
};

export const attemptLoginAction = (): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        // login should fail, just load search actions
        dispatch(loadSearchSettingsAction(null));
        return;
    }
    axios.get<User>(`${API_BASE_PATH}/user`, {withCredentials: true})
        .then(response => {
            const user = response.data;
            dispatch(loginSuccessAction(user));
            // @ts-ignore
            dispatch(loadSearchSettingsAction(user.roles.indexOf("ADMIN") != -1));
        })
        .catch(() => {
            // load search settings even when not logged in
            dispatch(loadSearchSettingsAction(null));
        });
}


export const userSettingsUpdateRequest = (user: User, onDone: () => void, onError: () => void): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockUpdateUserSettings(user.userSettings, onDone, dispatch);
        return;
    }
    axios.put(`${API_BASE_PATH}/user`, user, {withCredentials: true})
        .then(() => {
            dispatch(userSettingsUpdatedAction(user.userSettings));
            dispatch(openSnackBar("User settings updated"));
            onDone();
        })
        .catch(() => {
            dispatch(openSnackBar("Could not update user settings"));
            onError();
        });
}

export const changeUserPasswordRequestAction = (user: User, oldPassword: String, newPassword: string, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockChangePassword(user, newPassword, dispatch);
        return;
    }
    dispatch(changePasswordDialogShowProgressAction());
    axios.put(`${API_BASE_PATH}/user/password`, {
        login: user.login,
        oldPassword,
        newPassword
    }, {withCredentials: true})
        .then(() => {
            dispatch(openSnackBar(`Password changed successfully`));
            dispatch(changePasswordDialogHideProgressAction());
            dispatch(changePasswordDialogClosedAction());
        })
        .catch(error => {
            if (error.response.data.status === 400) {
                onError(parseValidationErrors(error));
            }
            dispatch(openSnackBar(`Could  not change password`));
            dispatch(changePasswordDialogHideProgressAction());
        })
};