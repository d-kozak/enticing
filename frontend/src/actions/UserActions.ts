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
import {UserSettings as UserSettingsModel, UserSettings} from "../entities/UserSettings";

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
    settings: SearchSettings
}

export type UserAction =
    LoginSuccessAction
    | LogoutAction
    | UserSettingsUpdatedAction
    | UserSearchSettingsSelectedSuccessAction;

export const logoutSuccessAction = (): LogoutAction => ({type: USER_LOGOUT});

export const userSearchSettingsSelectedSuccessAction = (settings: SearchSettings): UserSearchSettingsSelectedSuccessAction => ({
    type: USER_SEARCH_SETTINGS_SELECTED_SUCCESS,
    settings
});

export const userSettingsUpdatedAction = (userSettings: UserSettings): UserSettingsUpdatedAction => ({
    type: USER_SETTINGS_UPDATED,
    userSettings
})

export const logoutRequestAction = (): ThunkResult<void> => dispatch => {
    mockLogout(dispatch);
};

export const loginSuccessAction = (user: User): LoginSuccessAction => ({
    type: USER_LOGIN_SUCCESS,
    user
});

export const searchSettingsSelectedRequestAction = (settings: SearchSettings): ThunkResult<void> => (dispatch) => {
    mockUserSettingsSelectedRequest(settings, dispatch);
};

export const loginRequestAction = (login: string, password: string, onError: (errors: { login?: string, password?: string }) => void): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockLogin(login, password, dispatch, onError);
        return;
    }
    const formData = new FormData()
    formData.set("username", login)
    formData.set("password", password)

    axios.post<User>(`${API_BASE_PATH}/login`, formData)
        .then(response => {
            const user = response.data
            user.roles = new Set(user.roles) // transform array into set
            dispatch(loginSuccessAction(user));

            dispatch(openSnackBar('Logged in'));
            // @ts-ignore
            dispatch(loadSearchSettingsAction(response.data.isAdmin));
        }).catch(error => {
        if (error && (error.login || error.password)) {
            onError(error);
        } else {
            onError({login: 'Invalid login or password'});
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
            console.error(error);
            onError(error);
        }
    )
};

export const userSettingsUpdateRequest = (settings: UserSettingsModel, onDone: () => void, onError: () => void): ThunkResult<void> => (dispatch) => {
    mockUpdateUserSettings(settings, onDone, dispatch)
}

export const changeUserPasswordRequestAction = (user: User, newPassword: string): ThunkResult<void> => (dispatch) => {
    mockChangePassword(user, newPassword, dispatch);
};