import {Dispatch} from "redux";
import {openSnackBar} from "../actions/SnackBarActions";
import {loginSuccessAction} from "../actions/UserActions";


interface User {
    login: string,
    password: string,
    isAdmin: boolean
}

const users = new Map<string, User>([
    [
        'user1', {
        login: 'user1',
        password: 'user1',
        isAdmin: false
    }
    ],
    [
        'admin', {
        login: 'admin',
        password: 'admin',
        isAdmin: true
    }
    ]
]);


export const mockSignup = (login: string, password: string, dispatch: Dispatch, onError: (error: any) => void) => {
    setTimeout(() => {
        const user = users.get(login);
        if (!user) {
            users.set(login, {
                login,
                password,
                isAdmin: false
            });
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
        const user = users.get(login);
        if (user) {
            if (user.password === password) {
                dispatch(openSnackBar('Logged in'));
                dispatch(loginSuccessAction(login, user.isAdmin));
            } else {
                onError({password: 'Invalid password'});
            }
        } else {
            onError({login: 'Unknown login'});
        }
    }, 2000);
}