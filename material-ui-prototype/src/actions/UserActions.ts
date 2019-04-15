import {ThunkResult} from "./RootAction";
import {openSnackBar} from "./SnackBarActions";

type LogoutAction = {
    type: "[USER] LOGOUT"
}

type LoginRequestAction = {
    type: "[USER] LOGIN REQUEST"
    username: string,
    password: string,
    onError: (errors: any) => void
}

type LoginSuccessAction = {
    type: "[USER] LOGIN SUCCESS",
    username: string,
    isAdmin: boolean
}

export type UserAction = LoginSuccessAction | LogoutAction

export const logoutAction = (): LogoutAction => ({type: "[USER] LOGOUT"});

export const loginSuccessAction = (username: string, isAdmin: boolean): LoginSuccessAction => ({
    type: "[USER] LOGIN SUCCESS",
    username,
    isAdmin
})


export const loginRequestAction = (login: string, password: string, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    setTimeout(() => {
        if (login == 'admin' && password == 'admin') {
            dispatch(openSnackBar('Logged in'))
            dispatch(loginSuccessAction(login, true))
        } else if (login != 'dkozak' && password != 'dkozak') {
            dispatch(loginSuccessAction(login, true))
            dispatch(loginSuccessAction(login, false))
        } else if (login == 'dkozak' && password != 'dkozak') {
            onError({login: 'Unknown login'});
        } else if (login != 'dkozak' && password == 'dkozak') {
            onError({password: 'Invalid password'})
        } else {
            onError({login: 'Unknown login'});
        }
    }, 2000);
}
