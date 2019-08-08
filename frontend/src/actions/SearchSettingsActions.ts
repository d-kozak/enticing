import {isSearchSettingsContent, SearchSettings} from "../entities/SearchSettings";
import {ThunkResult} from "./RootActions";
import {uploadFile} from "../utils/file";
import {API_BASE_PATH} from "../globals";
import axios from "axios";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {parseValidationErrors} from "./errors";
import {corpusFormatLoadedAction, CorpusFormatLoadedAction} from "./CorpusFormatActions";
import {isCorpusFormat} from "../entities/CorpusFormat";
import {UserState} from "../reducers/ApplicationState";
import {openSnackbar} from "../reducers/SnackBarReducer";

export const SEARCH_SETTINGS_LOADED = "[SEARCH SETTINGS] LOADED";
export const SEARCH_SETTINGS_ADDED = "[SEARCH SETTINGS] ADDED";
export const SEARCH_SETTINGS_UPDATED = "[SEARCH SETTINGS] UPDATED";
export const SEARCH_SETTINGS_REMOVED = "[SEARCH SETTINGS] REMOVED";
export const SEARCH_SETTINGS_NEW_DEFAULT = "[SEARCH SETTINGS] NEW DEFAULT";
export const SEARCH_SETTINGS_ADDING_CANCELLED = "[SEARCH SETTINGS] ADDING CANCELLED"

interface SearchSettingsLoadedAction {
    type: typeof SEARCH_SETTINGS_LOADED,
    settings: Array<SearchSettings>
}

interface SearchSettingsAddedAction {
    type: typeof SEARCH_SETTINGS_ADDED,
    settings: SearchSettings
}

interface SearchSettingsUpdatedAction {
    type: typeof SEARCH_SETTINGS_UPDATED,
    settings: SearchSettings
}

interface SearchSettingsRemovedAction {
    type: typeof SEARCH_SETTINGS_REMOVED,
    settings: SearchSettings
}

interface SearchSettingsNewDefaultAction {
    type: typeof SEARCH_SETTINGS_NEW_DEFAULT,
    settings: SearchSettings
}

interface SearchSettingsAddingCancelledAction {
    type: typeof SEARCH_SETTINGS_ADDING_CANCELLED
}

export type SearchSettingsAction =
    SearchSettingsLoadedAction
    | SearchSettingsAddedAction
    | SearchSettingsUpdatedAction
    | SearchSettingsRemovedAction
    | SearchSettingsNewDefaultAction
    | SearchSettingsAddingCancelledAction
    | CorpusFormatLoadedAction // TODO add separate reducer or merge totally
    ;

export const searchSettingsLoadedAction = (settings: Array<SearchSettings>): SearchSettingsLoadedAction => ({
    type: SEARCH_SETTINGS_LOADED,
    settings
});

export const searchSettingsAddedAction = (settings: SearchSettings): SearchSettingsAddedAction => ({
    type: SEARCH_SETTINGS_ADDED,
    settings
});

export const searchSettingsUpdatedAction = (settings: SearchSettings): SearchSettingsUpdatedAction => ({
    type: SEARCH_SETTINGS_UPDATED,
    settings
});

export const searchSettingsRemovedAction = (settings: SearchSettings): SearchSettingsRemovedAction => ({
    type: SEARCH_SETTINGS_REMOVED,
    settings
});

export const searchSettingsNewDefaultAction = (settings: SearchSettings): SearchSettingsNewDefaultAction => ({
    type: SEARCH_SETTINGS_NEW_DEFAULT,
    settings
});

export const searchSettingsAddingCancelledAction = (): SearchSettingsAddingCancelledAction => ({
    type: SEARCH_SETTINGS_ADDING_CANCELLED
})

export const loadSearchSettingsAction = (userState: UserState | null): ThunkResult<void> => (dispatch) => {
    // todo use selector
    const isAdmin = (userState && userState.user && userState.user.roles.indexOf("ADMIN") !== -1) || false; // || false just here to make type system happy :X....
    axios.get<Array<SearchSettings>>(`${API_BASE_PATH}/search-settings`, {withCredentials: true})
        .then(response => {
            const searchSettings = response.data;
            dispatch(searchSettingsLoadedAction(searchSettings));
            if (searchSettings.length == 0) {
                dispatch(openSnackbar('No search settings loaded'));
            }
            const selectedSettings = findSelectedSettings(userState, searchSettings);
            if (selectedSettings !== null) {
                axios.get(`${API_BASE_PATH}/query/format/${selectedSettings.id}`, {withCredentials: true})
                    .then(response => {
                        if (isCorpusFormat(response.data)) {
                            dispatch(corpusFormatLoadedAction(Number(selectedSettings.id), response.data))
                        } else {
                            throw "could not parse";
                        }
                    }).catch(error => {
                    console.error(error);
                    dispatch(openSnackbar(`Could load format for selected configuration ${selectedSettings.name}`));
                })
            } else {
                console.warn("No selected search settings found, could not pre-load corpus format...")
            }
        })
        .catch(() => {
            dispatch(openSnackbar('Could not load configurations'));
        });
}

const findSelectedSettings = (userState: UserState | null, searchSettings: Array<SearchSettings>): SearchSettings | null => {
    if (userState !== null && userState.selectedSettings !== null) {
        for (let i in searchSettings) {
            if (searchSettings[i].id == userState.selectedSettings) {
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
    return null
};

export const updateSearchSettingsRequestAction = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    axios.put(`${API_BASE_PATH}/search-settings`, settings, {withCredentials: true})
        .then(() => {
            dispatch(searchSettingsUpdatedAction(settings))
            dispatch(openSnackbar(`Search settings ${settings.name} updated`));
            onDone();
        })
        .catch((response) => {
            const errors = response.response.data.status === 400 ? parseValidationErrors(response) : {}
            onError(errors);
            dispatch(openSnackbar(`Failed to updated ${settings.name}`));
        });
};


export const addEmptySearchSettingsRequestAction = (): ThunkResult<void> => (dispatch) => {
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
    dispatch(searchSettingsAddedAction(newSettings));
};

export const loadSettingsFromFileAction = (file: File): ThunkResult<void> => (dispatch) => {
    uploadFile(file, content => {
        try {
            const settings = JSON.parse(content);
            if (isSearchSettingsContent(settings)) {
                dispatch(showProgressbar());
                axios.post<SearchSettings>(`${API_BASE_PATH}/search-settings/import`, settings, {withCredentials: true})
                    .then((response) => {
                        dispatch(searchSettingsAddedAction(response.data))
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

export const saveNewSearchSettingsAction = (searchSettings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    axios.post<SearchSettings>(`${API_BASE_PATH}/search-settings`, searchSettings, {withCredentials: true})
        .then((response) => {
            dispatch(openSnackbar(`Search settings ${searchSettings.name} added`));
            response.data.isTransient = true; // reducer has to recognize this as a newly added search settings
            dispatch(searchSettingsUpdatedAction(response.data));
        })
        .catch((response) => {
            const errors = response.response.data.status === 400 ? parseValidationErrors(response) : {}
            onError(errors);
            dispatch(openSnackbar(`Adding search settings ${searchSettings.name} failed`));
        })
}

export const removeSearchSettingsRequestAction = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    axios.delete(`${API_BASE_PATH}/search-settings/${settings.id}`, {withCredentials: true})
        .then(() => {
            dispatch(searchSettingsRemovedAction(settings))
            dispatch(openSnackbar(`Search settings ${settings.name} removed`));
        }).catch(() => {
        dispatch(openSnackbar(`Failed to remove search settings ${settings.name}`));
        onError({})
    })
};


export const changeDefaultSearchSettingsRequestAction = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    axios.put(`${API_BASE_PATH}/search-settings/default/${settings.id}`, null, {withCredentials: true})
        .then(() => {
            dispatch(searchSettingsNewDefaultAction(settings))
            dispatch(openSnackbar(`Search settings ${settings.name} were made default`));
            onDone();
        })
        .catch(() => {
            dispatch(openSnackbar(`Could not make ${settings.name} default`));
            onError({})
        })
};



