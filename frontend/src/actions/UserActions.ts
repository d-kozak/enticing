import {ThunkResult} from "./RootActions";
import {mockLogin, mockSignup} from "../mocks/mockUserApi";
import {openSnackBar} from "./SnackBarActions";

export const USER_LOGOUT = "[USER] LOGOUT";
export const USER_LOGIN_SUCCESS = "[USER] LOGIN SUCCESS";

type LogoutAction = {
    type: typeof USER_LOGOUT
}
type LoginSuccessAction = {
    type: typeof USER_LOGIN_SUCCESS,
    username: string,
    isAdmin: boolean
}

export type UserAction = LoginSuccessAction | LogoutAction;

export const logoutSuccessAction = (): LogoutAction => ({type: USER_LOGOUT});

export const logoutRequestAction = (): ThunkResult<void> => dispatch => {
    dispatch(openSnackBar('Logged out'));
    dispatch(logoutSuccessAction());
};

export const loginSuccessAction = (username: string, isAdmin: boolean): LoginSuccessAction => ({
    type: USER_LOGIN_SUCCESS,
    username,
    isAdmin
});


export const loginRequestAction = (login: string, password: string, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    mockLogin(login, password, dispatch, onError);
};


export const signUpAction = (login: string, password: string, onError: (error: any) => void): ThunkResult<void> => (dispatch) => {
    mockSignup(login, password, dispatch, onError);
};