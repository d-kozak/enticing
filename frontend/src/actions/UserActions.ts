import {ThunkResult} from "./RootActions";
import {
    mockChangePassword,
    mockLogin,
    mockLogout,
    mockSignup,
    mockUserSettingsSelectedRequest
} from "../mocks/mockUserApi";
import {SearchSettings} from "../entities/SearchSettings";
import {User} from "../entities/User";
import axios from "axios";
import {API_BASE_PATH, useMockApi} from "../globals";

export const USER_LOGOUT = "[USER] LOGOUT";
export const USER_LOGIN_SUCCESS = "[USER] LOGIN SUCCESS";
export const USER_SEARCH_SETTINGS_SELECTED_SUCCESS = "[USER] SETTINGS SELECTED SUCCESS";

interface LogoutAction {
    type: typeof USER_LOGOUT
}

interface LoginSuccessAction {
    type: typeof USER_LOGIN_SUCCESS,
    user: User
}

interface UserSearchSettingsSelectedSuccessAction {
    type: typeof USER_SEARCH_SETTINGS_SELECTED_SUCCESS,
    settings: SearchSettings
}

export type UserAction = LoginSuccessAction | LogoutAction | UserSearchSettingsSelectedSuccessAction;

export const logoutSuccessAction = (): LogoutAction => ({type: USER_LOGOUT});

export const userSearchSettingsSelectedSuccessAction = (settings: SearchSettings): UserSearchSettingsSelectedSuccessAction => ({
    type: USER_SEARCH_SETTINGS_SELECTED_SUCCESS,
    settings
});

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
    axios.post(`${API_BASE_PATH}/login`, JSON.stringify({
        username: login,
        password
    })).then(smth => console.log(smth))
        .catch(error => {
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
    axios.post(`${API_BASE_PATH}/user`, JSON.stringify({
        login, password
    })).then(() => {
            console.log('time to log in');
        }
    ).catch(error => {
            console.error(error);
            onError(error);
        }
    )
};

export const changeUserPasswordRequestAction = (user: User, newPassword: string): ThunkResult<void> => (dispatch) => {
    mockChangePassword(user, newPassword, dispatch);
};