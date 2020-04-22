import {User, UserCredentials} from "../entities/user";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest, postRequest} from "../network/requests";
import {ThunkDispatch} from "redux-thunk";
import {ApplicationState} from "../ApplicationState";
import {AnyAction} from "redux";
import {openSnackbarAction} from "../reducers/snackbarReducer";
import {loginSuccessAction, logoutSuccessAction} from "../reducers/userDetailsReducer";

async function snackbarOnError(dispatch: ThunkDispatch<ApplicationState, undefined, AnyAction>, message: string, block: () => Promise<void>) {
    try {
        await block();
    } catch (e) {
        console.error(e);
        dispatch(openSnackbarAction(message));
    }

}

export const loginRequest = (credentials: UserCredentials, onDone?: () => void): ThunkResult<void> => async (dispatch) => {
    await snackbarOnError(dispatch, "Failed to log in, please check your credentials", async () => {
        const user = await postRequest<User>("/login", credentials);
        dispatch(loginSuccessAction(user))
    });
    if (onDone) onDone();
}

export const logoutRequest = (): ThunkResult<void> => async (dispatch) => {
    await snackbarOnError(dispatch, "Failed to logout", async () => {
        await getRequest<User>("/logout");
        dispatch(logoutSuccessAction())
    });
}