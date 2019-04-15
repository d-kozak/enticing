import {Dispatch} from "redux";
import {openSnackBar} from "../actions/SnackBarActions";
import {loginSuccessAction} from "../actions/UserActions";

export const mockSignup = (login: string, dispatch: Dispatch, onError: (error: any) => void) => {
    setTimeout(() => {
        if (login != 'dkozak') {
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