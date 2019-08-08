import {ThunkResult} from "./RootActions";
import {SearchSettings} from "../entities/SearchSettings";
import {User} from "../entities/User";
import axios from "axios";
import {API_BASE_PATH} from "../globals";
import {loadSearchSettingsAction} from "./SearchSettingsActions";
import {UserSettings} from "../entities/UserSettings";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {
    changePasswordDialogClosedAction,
    changePasswordDialogHideProgressAction,
    changePasswordDialogShowProgressAction
} from "./dialog/ChangePasswordDialogActions";
import {parseValidationErrors} from "./errors";
import {isCorpusFormat} from "../entities/CorpusFormat";
import {corpusFormatLoadedAction} from "./CorpusFormatActions";
import {openSnackbar} from "../reducers/SnackBarReducer";

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
    dispatch(showProgressbar());
    axios.get(`${API_BASE_PATH}/logout`)
        .then(() => {
            dispatch(openSnackbar('Logged out'));
            // @ts-ignore
            dispatch(loadSearchSettingsAction(null));
            dispatch(logoutSuccessAction());
            dispatch(hideProgressbar());
        })
        .catch(error => {
            dispatch(openSnackbar('Could not logout'));
            dispatch(hideProgressbar());
        });
};

export const loginSuccessAction = (user: User): LoginSuccessAction => ({
    type: USER_LOGIN_SUCCESS,
    user
});

export const searchSettingsSelectedRequestAction = (settings: SearchSettings, previousSelectedSettings: string, isLoggedIn: boolean): ThunkResult<void> => (dispatch) => {
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
            dispatch(openSnackbar(`Failed to select settings ${settings.name}`));
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
                dispatch(openSnackbar(`Failed to load corpus format for settings ${settings.name}`));
            })
    }
};

export const loginRequestAction = (login: string, password: string, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    const formData = new FormData()
    formData.set("username", login)
    formData.set("password", password)

    axios.post(`${API_BASE_PATH}/login`, formData, {withCredentials: true})
        .then(response => {
            const user = response.data
            dispatch(loginSuccessAction(user));

            dispatch(openSnackbar('Logged in'));
            // @ts-ignore
            dispatch(loadSearchSettingsAction(user.roles.indexOf("ADMIN") != -1));
        })
        .catch(error => {
            if (error.response.data.status === 401) {
                onError({login: 'Invalid login or password'});
            } else {
                dispatch(openSnackbar('Could not log in'));
                onError({});
            }
        });
};


export const signUpAction = (login: string, password: string, onError: (error: any) => void): ThunkResult<void> => (dispatch) => {
    axios.post(`${API_BASE_PATH}/user`, {
        login, password
    }).then(() => {
        dispatch(loginRequestAction(login, password, () => {
            dispatch(openSnackbar('Signup was successful, but subsequent login failed, please try to log in manually'))
        }))
        }
    ).catch(error => {
        if (error.response.data.status === 400) {
            onError(parseValidationErrors(error));
        } else {
            dispatch(openSnackbar('Could not log in'));
            onError({});
        }
        }
    )
};

export const attemptLoginAction = (): ThunkResult<void> => (dispatch) => {
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
    axios.put(`${API_BASE_PATH}/user`, user, {withCredentials: true})
        .then(() => {
            dispatch(userSettingsUpdatedAction(user.userSettings));
            dispatch(openSnackbar("User settings updated"));
            onDone();
        })
        .catch(() => {
            dispatch(openSnackbar("Could not update user settings"));
            onError();
        });
}

export const changeUserPasswordRequestAction = (user: User, oldPassword: String, newPassword: string, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    dispatch(changePasswordDialogShowProgressAction());
    axios.put(`${API_BASE_PATH}/user/password`, {
        login: user.login,
        oldPassword,
        newPassword
    }, {withCredentials: true})
        .then(() => {
            dispatch(openSnackbar(`Password changed successfully`));
            dispatch(changePasswordDialogHideProgressAction());
            dispatch(changePasswordDialogClosedAction());
        })
        .catch(error => {
            if (error.response.data.status === 400) {
                onError(parseValidationErrors(error));
            }
            dispatch(openSnackbar(`Could  not change password`));
            dispatch(changePasswordDialogHideProgressAction());
        })
};