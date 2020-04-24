import {User, UserCredentials} from "../entities/user";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest, postRequest} from "../network/requests";
import {ThunkDispatch} from "redux-thunk";
import {ApplicationState} from "../ApplicationState";
import {AnyAction} from "redux";
import {openSnackbarAction} from "../reducers/snackbarReducer";
import {loginSuccessAction, logoutSuccessAction} from "../reducers/userDetailsReducer";
import * as H from 'history';

export async function snackbarOnError(dispatch: ThunkDispatch<ApplicationState, undefined, AnyAction>, message: string, block: () => Promise<void>): Promise<boolean> {
    try {
        await block();
        return true;
    } catch (e) {
        console.error(e);
        dispatch(openSnackbarAction(message));
        return false;
    }

}

export const loginRequest = (credentials: UserCredentials, onDone?: (sucess: boolean) => void): ThunkResult<void> => async (dispatch) => {
    const success = await snackbarOnError(dispatch, "Failed to log in, please check your credentials", async () => {
        const formData = new FormData();
        formData.set("username", credentials.login);
        formData.set("password", credentials.password);
        const user = await postRequest<User>("/login", formData);

        dispatch(loginSuccessAction(user))
    });
    if (onDone) onDone(success);
}

export const logoutRequest = (history: H.History): ThunkResult<void> => async (dispatch) => {
    await snackbarOnError(dispatch, "Failed to logout", async () => {
        await getRequest<User>("/logout");
        history.push("/login")
        dispatch(logoutSuccessAction())
    });
}