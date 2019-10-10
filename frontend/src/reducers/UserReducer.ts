import {ApplicationState, UserState} from "../ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {createAnonymousUser, User} from "../entities/User";
import {UserSettings} from "../entities/UserSettings";
import {ThunkResult} from "../actions/RootActions";
import {hideProgressbar, showProgressbar} from "./ProgressBarReducer";
import axios from "axios";
import {API_BASE_PATH} from "../globals";
import {openSnackbar} from "./SnackBarReducer";
import {SearchSettings} from "../entities/SearchSettings";
import {CorpusFormat, isCorpusFormat} from "../entities/CorpusFormat";
import {doCorpusFormatRequest, loadCorpusFormat, loadSearchSettingsRequest} from "./SearchSettingsReducer";
import {parseValidationErrors} from "../actions/errors";
import {
    closeChangePasswordDialog,
    hideChangePasswordDialogProgress,
    showChangePasswordDialogProgress
} from "./dialog/ChangePasswordDialogReducer";
import {isSelectedMetadata, SelectedMetadata} from "../entities/SelectedMetadata";
import {consoleDump} from "../components/utils/dump";

const {reducer, actions} = createSlice({
    slice: 'user',
    initialState: {
        user: createAnonymousUser(),
        isLoggedIn: false
    } as UserState,
    reducers: {
        userLogin: (state: UserState, {payload}: PayloadAction<User>) => {
            state.user = payload;
            if (!state.user.selectedMetadata) {
                state.user.selectedMetadata = {};
            }
            state.isLoggedIn = true;
        },
        userLogout: (state: UserState) => {
            state.user = createAnonymousUser();
            state.isLoggedIn = false;
        },
        updateUserSettings: (state: UserState, {payload}: PayloadAction<UserSettings>) => {
            state.user.userSettings = payload;
        },
        selectSearchSettings: (state: UserState, {payload}: PayloadAction<string>) => {
            state.user.selectedSettings = payload;
        },
        loadSelectedMetadata: (state: UserState, {payload}: PayloadAction<{ settingsId: string, metadata: SelectedMetadata }>) => {
            state.user.selectedMetadata[payload.settingsId] = payload.metadata;
        }
    }
});


export const isUserAdmin = (state: ApplicationState) => state.userState.user.roles.indexOf("ADMIN") != -1;
export const isLoggedIn = (state: ApplicationState) => state.userState.isLoggedIn;
export const getUser = (state: ApplicationState) => state.userState.user;

export const {userLogin, userLogout, updateUserSettings, selectSearchSettings, loadSelectedMetadata} = actions;
export default reducer;

export const saveDefaultMetadataRequest = (metadata: SelectedMetadata, settingsId: string): ThunkResult<void> => async (dispatch) => {
    if (metadata.indexes.indexOf(metadata.defaultIndex) < 0) {
        metadata = {
            ...metadata,
            indexes: [...metadata.indexes, metadata.defaultIndex]
        };
        dispatch(openSnackbar(`Default index '${metadata.defaultIndex}' was added automatically`));
    }
    try {
        const response = await axios.post(`${API_BASE_PATH}/user/default-metadata/${settingsId}`, metadata, {withCredentials: true});
        dispatch(openSnackbar(`Default metadata saved successfully`));
    } catch (e) {
        consoleDump(e);
        dispatch(openSnackbar(`Failed to save default metadata`));
    }
};

/**
 * Ensures that the ordering of elements in SelectedData is consistent with CorpusFormat.
 * Otherwise received data can be misinterpreted ( the order of columns could be wrong)
 * @param data
 * @param corpusFormat
 */
export function orderMetadata(data: SelectedMetadata, corpusFormat: CorpusFormat): SelectedMetadata {
    const indexes: Array<string> = [];
    const entities: { [key: string]: { attributes: Array<string>, color: string } } = {};

    for (let index of Object.keys(corpusFormat.indexes)) {
        if (data.indexes.indexOf(index) >= 0) indexes.push(index);
    }

    for (let entityName of Object.keys(corpusFormat.entities)) {
        const wantedEntityInfo = data.entities[entityName];
        if (wantedEntityInfo) {
            const fullEntity = corpusFormat.entities[entityName];
            const attributes: Array<string> = [];
            for (let attribute of Object.keys(fullEntity.attributes)) {
                if (wantedEntityInfo.attributes.indexOf(attribute) >= 0) attributes.push(attribute);
            }
            entities[entityName] = {
                attributes,
                color: wantedEntityInfo.color
            };
        }
    }

    return {
        indexes,
        entities,
        defaultIndex: data.defaultIndex
    }
}

export const doLoadSelectedMetadata = async (searchSettings: SearchSettings, isLoggedIn: boolean): Promise<SelectedMetadata> => {
    const path = isLoggedIn ? `${API_BASE_PATH}/user/text-metadata/${searchSettings.id}` : `${API_BASE_PATH}/user/default-metadata/${searchSettings.id}`;
    const {data} = await axios.get<SelectedMetadata>(path);
    if (!isSelectedMetadata(data)) {
        console.error("invalid format of selected metadata");
        consoleDump(data);
    }
    return data;
};

export const loadSelectedMetadataRequest = (searchSettingsId: string): ThunkResult<void> => async (dispatch, getState) => {
    const searchSettings = getState().searchSettings.settings[searchSettingsId];
    if (!searchSettings) {
        dispatch(openSnackbar(`Cannot load metadata for search settings ${searchSettingsId}, which is  unknown`));
        return;
    }
    let corpusFormat = searchSettings.corpusFormat;
    if (!corpusFormat) {
        console.warn(`No corpus format is loaded for search settings ${searchSettingsId}, loading it now`);
        corpusFormat = await doCorpusFormatRequest(searchSettings);
    }
    try {
        const data = await doLoadSelectedMetadata(searchSettings, isLoggedIn(getState()));
        dispatch(loadSelectedMetadata({
            settingsId: searchSettingsId,
            metadata: orderMetadata(data, corpusFormat!)
        }))
    } catch (e) {
        consoleDump(e);
        dispatch(openSnackbar(`Could not load selected metadata for settings ${searchSettingsId}`));
    }
};

export const saveSelectedMetadataRequest = (metadata: SelectedMetadata, settingsId: string): ThunkResult<void> => async (dispatch, getState) => {
    if (metadata.indexes.indexOf(metadata.defaultIndex) < 0) {
        // todo do we have to preserve the order of indexes according the the corpus format???
        metadata = {
            ...metadata,
            indexes: [...metadata.indexes, metadata.defaultIndex]
        };
        dispatch(openSnackbar(`Default index '${metadata.defaultIndex}' was added automatically`));
    }
    const isLoggedIn = getState().userState.isLoggedIn;
    try {
        if (isLoggedIn) {
            await axios.post(`${API_BASE_PATH}/user/text-metadata/${settingsId}`, metadata, {withCredentials: true});
        }
        dispatch(loadSelectedMetadata({metadata, settingsId}));
        dispatch(openSnackbar(`Metadata selected`));
    } catch (e) {
        dispatch(openSnackbar(`Failed save selected metadata`));
    }
};

export const logoutRequest = (): ThunkResult<void> => dispatch => {
    dispatch(showProgressbar());
    axios.get(`${API_BASE_PATH}/logout`)
        .then(() => {
            dispatch(openSnackbar('Logged out'));
            dispatch(loadSearchSettingsRequest(null, false));
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
                    dispatch(loadCorpusFormat({id: Number(settings.id), format: response.data}));
                    if (isLoggedIn)
                        dispatch(loadSelectedMetadataRequest(settings.id));
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
            dispatch(loadSearchSettingsRequest(user.selectedSettings, true));
        }).catch(error => {
        if (error.response.data.status === 401) {
            onError({login: 'Invalid login or password'});
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
                onError({});
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
            dispatch(loadSearchSettingsRequest(user.selectedSettings, true));
        })
        .catch(() => {
            // load search settings even when not logged in
            dispatch(loadSearchSettingsRequest(null, false));
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