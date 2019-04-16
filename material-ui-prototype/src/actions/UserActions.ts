import {ThunkResult} from "./RootAction";
import {mockLogin, mockSignup} from "../mocks/mockUserApi";

type LogoutAction = {
    type: "[USER] LOGOUT"
}

type LoginSuccessAction = {
    type: "[USER] LOGIN SUCCESS",
    username: string,
    isAdmin: boolean
}

export type UserAction = LoginSuccessAction | LogoutAction;

export const logoutAction = (): LogoutAction => ({type: "[USER] LOGOUT"});

export const loginSuccessAction = (username: string, isAdmin: boolean): LoginSuccessAction => ({
    type: "[USER] LOGIN SUCCESS",
    username,
    isAdmin
});


export const loginRequestAction = (login: string, password: string, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    mockLogin(login, password, dispatch, onError);
};


export const signUpAction = (login: string, password: string, onError: (error: any) => void): ThunkResult<void> => (dispatch) => {
    mockSignup(login, password, dispatch, onError);
};