import {ThunkResult} from "./RootActions";
import {mockLogin, mockLogout, mockSignup, mockUserSettingsSelectedRequest} from "../mocks/mockUserApi";
import {SearchSettings} from "../entities/SearchSettings";

export const USER_LOGOUT = "[USER] LOGOUT";
export const USER_LOGIN_SUCCESS = "[USER] LOGIN SUCCESS";
export const USER_SEARCH_SETTINGS_SELECTED_SUCCESS = "[USER] SETTINGS SELECTED SUCCESS";

interface LogoutAction {
    type: typeof USER_LOGOUT
}

interface LoginSuccessAction {
    type: typeof USER_LOGIN_SUCCESS,
    username: string,
    isAdmin: boolean
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

export const loginSuccessAction = (username: string, isAdmin: boolean): LoginSuccessAction => ({
    type: USER_LOGIN_SUCCESS,
    username,
    isAdmin
});

export const searchSettingsSelectedRequestAction = (settings: SearchSettings): ThunkResult<void> => (dispatch) => {
    mockUserSettingsSelectedRequest(settings, dispatch);
};

export const loginRequestAction = (login: string, password: string, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    mockLogin(login, password, dispatch, onError);
};


export const signUpAction = (login: string, password: string, onError: (error: any) => void): ThunkResult<void> => (dispatch) => {
    mockSignup(login, password, dispatch, onError);
};