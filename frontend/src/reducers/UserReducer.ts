import {UserState} from "./ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {User} from "../entities/User";
import {UserSettings} from "../entities/UserSettings";
import {ThunkResult} from "../actions/RootActions";
import {hideProgressbar, showProgressbar} from "./ProgressBarReducer";
import axios from "axios";
import {API_BASE_PATH} from "../globals";
import {openSnackbar} from "./SnackBarReducer";
import {SearchSettings} from "../entities/SearchSettings";
import {isCorpusFormat} from "../entities/CorpusFormat";
import {loadCorpusFormat, loadSearchSettingsRequest} from "./SearchSettingsReducer";
import {parseValidationErrors} from "../actions/errors";
import {
    closeChangePasswordDialog,
    hideChangePasswordDialogProgress,
    showChangePasswordDialogProgress
} from "./dialog/ChangePasswordDialogReducer";


const {reducer, actions} = createSlice({
    slice: 'user',
    initialState: {
        user: null,
        selectedSettings: null,
        selectedMetadata: null
    } as UserState,
    reducers: {
        userLogin: (state: UserState, {payload}: PayloadAction<User>) => {
            state.user = payload;
            state.selectedSettings = payload.selectedSettings;
        },
        userLogout: (state: UserState) => {
            state.user = null;
            state.selectedSettings = null
        },
        updateUserSettings: (state: UserState, {payload}: PayloadAction<UserSettings>) => {
            if (!state.user) {
                throw new Error("Cannot update settings when no user is logged in")
            }
            state.user.userSettings = payload;
        },
        selectSearchSettings: (state: UserState, {payload}: PayloadAction<string>) => {
            if (!state.user) {
                throw new Error("Cannot update settings when no user is logged in")
            }
            state.user.selectedSettings = payload;
        }
    }
});


const {userLogin, userLogout, updateUserSettings, selectSearchSettings} = actions;
export default reducer;


export const logoutRequest = (): ThunkResult<void> => dispatch => {
    dispatch(showProgressbar());
    axios.get(`${API_BASE_PATH}/logout`)
        .then(() => {
            dispatch(openSnackbar('Logged out'));
            dispatch(loadSearchSettingsRequest(null));
            dispatch(userLogout());
            dispatch(hideProgressbar());
        })
        .catch(error => {
            dispatch(openSnackbar('Could not logout'));
            dispatch(hideProgressbar());
        });
};

export const selectSearchSettingsRequest = (settings: SearchSettings, previousSelectedSettings: string, isLoggedIn: boolean): ThunkResult<void> => (dispatch) => {
    dispatch(selectSearchSettings(settings.id));
    if (isLoggedIn) {
        axios.get(`${API_BASE_PATH}/search-settings/select/${settings.id}`, {withCredentials: true})
            .then(() => {
                return axios.get(`${API_BASE_PATH}/query/format/${settings.id}`, {withCredentials: true})
            })
            .then(response => {
                if (isCorpusFormat(response.data)) {
                    dispatch(loadCorpusFormat({id: Number(settings.id), format: response.data}))
                } else {
                    throw "cannot parse";
                }
            }).catch((error) => {
            console.error(error);
            dispatch(openSnackbar(`Failed to select settings ${settings.name}`));
            // rollback to previously selected
            dispatch(selectSearchSettings(previousSelectedSettings));
        })
    } else {
        // if not logged in, just load corpus format
        axios.get(`${API_BASE_PATH}/query/format/${settings.id}`, {withCredentials: true})
            .then(response => {
                if (isCorpusFormat(response.data)) {
                    dispatch(loadCorpusFormat({id: Number(settings.id), format: response.data}))
                } else {
                    throw "cannot parse";
                }
                dispatch(selectSearchSettings(settings.id));
            })
            .catch((error) => {
                console.error(error);
                dispatch(openSnackbar(`Failed to load corpus format for settings ${settings.name}`));
            })
    }
};

export const loginRequest = (login: string, password: string, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    const formData = new FormData();
    formData.set("username", login);
    formData.set("password", password);

    axios.post(`${API_BASE_PATH}/login`, formData, {withCredentials: true})
        .then(response => {
            const user = response.data;
            dispatch(userLogin(user));

            dispatch(openSnackbar('Logged in'));
            dispatch(loadSearchSettingsRequest(user.selectedSettings));
        })
        .catch(error => {
            if (error.response.data.status === 401) {
                onError({userLogin: 'Invalid login or password'});
            } else {
                dispatch(openSnackbar('Could not log in'));
                onError({});
            }
        });
};


export const signUpRequest = (login: string, password: string, onError: (error: any) => void): ThunkResult<void> => (dispatch) => {
    axios.post(`${API_BASE_PATH}/user`, {
        login, password
    }).then(() => {
            dispatch(loginRequest(login, password, () => {
                dispatch(openSnackbar('Signup was successful, but subsequent login failed, please try to log in manually'))
            }))
        }
    ).catch(error => {
            if (error.response.data.status === 400) {
                onError(parseValidationErrors(error));
            } else {
                dispatch(openSnackbar('Could not log in'));
                onError({});
            }
        }
    )
};

export const attemptLoginRequest = (): ThunkResult<void> => (dispatch) => {
    axios.get<User>(`${API_BASE_PATH}/user`, {withCredentials: true})
        .then(response => {
            const user = response.data;
            dispatch(userLogin(user));
            // @ts-ignore
            dispatch(loadSearchSettingsAction(user.roles.indexOf("ADMIN") != -1));
        })
        .catch(() => {
            // load search settings even when not logged in
            dispatch(loadSearchSettingsRequest(null));
        });
};


export const userSettingsUpdateRequest = (user: User, onDone: () => void, onError: () => void): ThunkResult<void> => (dispatch) => {
    axios.put(`${API_BASE_PATH}/user`, user, {withCredentials: true})
        .then(() => {
            dispatch(updateUserSettings(user.userSettings));
            dispatch(openSnackbar("User settings updated"));
            onDone();
        })
        .catch(() => {
            dispatch(openSnackbar("Could not update user settings"));
            onError();
        });
};

export const changePasswordRequest = (user: User, oldPassword: String, newPassword: string, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    dispatch(showChangePasswordDialogProgress());
    axios.put(`${API_BASE_PATH}/user/password`, {
        login: user.login,
        oldPassword,
        newPassword
    }, {withCredentials: true})
        .then(() => {
            dispatch(openSnackbar(`Password changed successfully`));
            dispatch(hideChangePasswordDialogProgress());
            dispatch(closeChangePasswordDialog());
        })
        .catch(error => {
            if (error.response.data.status === 400) {
                onError(parseValidationErrors(error));
            }
            dispatch(openSnackbar(`Could  not change password`));
            dispatch(hideChangePasswordDialogProgress());
        })
};