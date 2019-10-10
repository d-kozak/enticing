import {isSearchSettingsContent, SearchSettings} from "../entities/SearchSettings";
import {SearchSettingsState} from "../ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {CorpusFormat, isCorpusFormat} from "../entities/CorpusFormat";
import {ThunkResult} from "../actions/RootActions";
import axios from "axios";
import {API_BASE_PATH} from "../globals";
import {openSnackbar} from "./SnackBarReducer";
import {parseValidationErrors} from "../actions/errors";
import {uploadFile} from "../utils/file";
import {hideProgressbar, showProgressbar} from "./ProgressBarReducer";
import {
    doLoadSelectedMetadata,
    isLoggedIn,
    loadSelectedMetadata,
    loadSelectedMetadataRequest,
    orderMetadata
} from "./UserReducer";
import {consoleDump} from "../components/utils/dump";


const {reducer, actions} = createSlice({
    slice: 'searchSettings',
    initialState: {settings: {}} as SearchSettingsState,
    reducers: {
        loadSearchSettings: (state: SearchSettingsState, {payload}: PayloadAction<Array<SearchSettings>>) => {
            state.settings = {};
            for (let settings of payload) {
                state.settings[settings.id] = settings;
                settings.servers.sort();
            }
        },
        addSearchSettings: (state: SearchSettingsState, {payload}: PayloadAction<SearchSettings>) => {
            state.settings[payload.id] = payload;
        },
        updateSearchSettings: (state: SearchSettingsState, {payload}: PayloadAction<SearchSettings>) => {
            if (payload.isTransient)
                payload.isTransient = false;
            for (let settings of Object.values(state.settings)) {
                if (settings.isTransient) {
                    delete state.settings[settings.id];
                }
            }
            state.settings[payload.id] = payload;
        },
        removeSearchSettings: (state: SearchSettingsState, {payload}: PayloadAction<SearchSettings>) => {
            delete state.settings[payload.id];
        },
        setNewDefaultSettings: (state: SearchSettingsState, {payload}: PayloadAction<SearchSettings>) => {
            for (let settings of Object.values(state.settings)) {
                settings.default = settings.id == payload.id;
            }
        },
        removeTransientSettings: (state: SearchSettingsState) => {
            for (let settings of Object.values(state.settings)) {
                if (settings.isTransient) {
                    delete state.settings[settings.id];
                }
            }
        },
        loadCorpusFormat: (state: SearchSettingsState, {payload}: PayloadAction<{ id: number, format: CorpusFormat }>) => {
            const settings = state.settings[payload.id];
            if (settings) {
                settings.corpusFormat = payload.format;
            } else {
                throw new Error(`could save corpus format for settings with id ${payload.id}, not such settings found`)
            }
        }
    }
});

const {addSearchSettings, loadSearchSettings, removeSearchSettings, setNewDefaultSettings, updateSearchSettings} = actions;
export const {removeTransientSettings, loadCorpusFormat} = actions;
export default reducer;


export const doCorpusFormatRequest = async (searchSettings: SearchSettings): Promise<CorpusFormat> => {
    const response = await axios.get(`${API_BASE_PATH}/query/format/${searchSettings.id}`, {withCredentials: true});
    if (isCorpusFormat(response.data)) {
        return response.data;
    } else {
        throw "could not parse";
    }
};

export const loadCorpusFormatRequest = (searchSettings: SearchSettings): ThunkResult<void> => async (dispatch) => {
    try {
        const corpusFormat = await doCorpusFormatRequest(searchSettings);
        dispatch(loadCorpusFormat({id: Number(searchSettings.id), format: corpusFormat}))
    } catch (e) {
        consoleDump(e);
        dispatch(openSnackbar(`Could load format for selected configuration ${searchSettings.name}`));
    }
};

export const loadCorpusFormatWithMetadataRequest = (searchSettingsId: string, useCached: boolean = true): ThunkResult<void> => async (dispatch, getState) => {
    const state = getState();
    const settings = state.searchSettings.settings[searchSettingsId];
    if (!settings) {
        console.error(`No search settings with id found ${searchSettingsId}`);
        dispatch(openSnackbar("Could not corpus format with metadata"));
        return;
    }
    try {
        let corpusFormat = settings.corpusFormat;
        if (!useCached || !settings.corpusFormat) {
            corpusFormat = await doCorpusFormatRequest(settings);
            dispatch(loadCorpusFormat({id: Number(searchSettingsId), format: corpusFormat}));
        }
        if (!useCached || !state.userState.user.selectedMetadata[searchSettingsId]) {
            const metadata = await doLoadSelectedMetadata(settings, isLoggedIn(state));
            dispatch(loadSelectedMetadata({
                settingsId: searchSettingsId,
                metadata: orderMetadata(metadata, corpusFormat!)
            }))
        }
    } catch (e) {
        consoleDump(e);
        dispatch(openSnackbar("Could not load corpus format with metadata"));
    }
};

export const loadSearchSettingsRequest = (selectedSettingsId: string | null, isLoggedIn: boolean): ThunkResult<void> => async (dispatch) => {
    try {
        const response = await axios.get<Array<SearchSettings>>(`${API_BASE_PATH}/search-settings`, {withCredentials: true});
        const searchSettings = response.data;
        dispatch(loadSearchSettings(searchSettings));
        if (searchSettings.length == 0) {
            dispatch(openSnackbar('No search settings loaded'));
        }
        const selectedSettings = findSelectedSettings(selectedSettingsId, searchSettings);
        if (selectedSettings !== null) {
            dispatch(loadCorpusFormatRequest(selectedSettings));
            dispatch(loadSelectedMetadataRequest(selectedSettings.id));
        } else {
            console.warn("No selected search settings found, could not pre-load corpus format...")
        }

    } catch (e) {
        dispatch(openSnackbar('Could not load configurations'));
    }
};

const findSelectedSettings = (selectedSettings: string | null, searchSettings: Array<SearchSettings>): SearchSettings | null => {
    if (selectedSettings !== null) {
        for (let i in searchSettings) {
            if (searchSettings[i].id == selectedSettings) {
                return searchSettings[i];
            }
        }
    } else {
        for (let i in searchSettings) {
            if (searchSettings[i].default) {
                return searchSettings[i];
            }
        }
    }
    if (searchSettings.length > 0) return searchSettings[0];
    return null
};

export const updateSearchSettingsRequest = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    axios.put(`${API_BASE_PATH}/search-settings`, settings, {withCredentials: true})
        .then(() => {
            dispatch(updateSearchSettings(settings));
            dispatch(openSnackbar(`Search settings ${settings.name} updated`));
            onDone();
        })
        .catch((response) => {
            const errors = response.response.data.status === 400 ? parseValidationErrors(response) : {}
            onError(errors);
            dispatch(openSnackbar(`Failed to updated ${settings.name}`));
        });
};


export const addTransientSearchSettings = (): ThunkResult<void> => (dispatch) => {
    const newSettings: SearchSettings = {
        id: "0",
        name: '',
        private: true,
        default: false,
        annotationDataServer: '',
        annotationServer: '',
        servers: [],
        isTransient: true
    };
    dispatch(addSearchSettings(newSettings));
};

export const loadSettingsFromFileRequest = (file: File): ThunkResult<void> => (dispatch) => {
    uploadFile(file, content => {
        try {
            const settings = JSON.parse(content);
            if (isSearchSettingsContent(settings)) {
                dispatch(showProgressbar());
                axios.post<SearchSettings>(`${API_BASE_PATH}/search-settings/import`, settings, {withCredentials: true})
                    .then((response) => {
                        dispatch(addSearchSettings(response.data));
                        dispatch(hideProgressbar());
                    })
                    .catch((response) => {
                        if (response.response.data.status == 400) {
                            dispatch(openSnackbar(`Could not import settings  - they are not valid`));
                            console.error(parseValidationErrors(response));
                        } else {
                            dispatch(openSnackbar(`Could not import settings ${settings.name}`));
                        }
                        dispatch(hideProgressbar());
                    })
            } else {
                dispatch(openSnackbar(`File ${file.name} does not contains valid settings`));
                console.error(settings);
            }
        } catch (e) {
            if (e instanceof SyntaxError) {
                dispatch(openSnackbar(`File ${file.name} does not contains valid JSON`));
            } else throw e;
        }
    });
};

export const saveNewSearchSettingsRequest = (searchSettings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    axios.post<SearchSettings>(`${API_BASE_PATH}/search-settings`, searchSettings, {withCredentials: true})
        .then((response) => {
            dispatch(openSnackbar(`Search settings ${searchSettings.name} added`));
            response.data.isTransient = true; // reducer has to recognize this as a newly added search settings
            dispatch(updateSearchSettings(response.data));
        })
        .catch((error) => {
            console.error(JSON.stringify(error, null, 2));
            const errors = error.response.status === 400 ? parseValidationErrors(error) : {};
            onError(errors);
            dispatch(openSnackbar(`Adding search settings ${searchSettings.name} failed`));
        })
}

export const removeSearchSettingsRequest = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    axios.delete(`${API_BASE_PATH}/search-settings/${settings.id}`, {withCredentials: true})
        .then(() => {
            dispatch(removeSearchSettings(settings));
            dispatch(openSnackbar(`Search settings ${settings.name} removed`));
        }).catch(() => {
        dispatch(openSnackbar(`Failed to remove search settings ${settings.name}`));
        onError({})
    })
};


export const changeDefaultSearchSettingsRequest = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    axios.put(`${API_BASE_PATH}/search-settings/default/${settings.id}`, null, {withCredentials: true})
        .then(() => {
            dispatch(setNewDefaultSettings(settings));
            dispatch(openSnackbar(`Search settings ${settings.name} were made default`));
            onDone();
        })
        .catch(() => {
            dispatch(openSnackbar(`Could not make ${settings.name} default`));
            onError({})
        })
};



