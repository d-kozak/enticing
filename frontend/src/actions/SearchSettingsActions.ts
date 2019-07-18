import {isSearchSettingsContent, SearchSettings} from "../entities/SearchSettings";
import {ThunkResult} from "./RootActions";
import {
    mockChangeDefaultSearchSettings,
    mockLoadSearchSettings,
    mockRemoveSearchSettings,
    mockSaveNewSearchSettings,
    mockUpdateSearchSettings,
    mockUploadSettings
} from "../mocks/mockSearchSettings";
import {uploadFile} from "../utils/file";
import {API_BASE_PATH, useMockApi} from "../globals";
import {openSnackBar} from "./SnackBarActions";
import axios from "axios";
import {hideProgressBarAction, showProgressBarAction} from "./ProgressBarActions";
import {parseValidationErrors} from "./errors";
import {CorpusFormatLoadedAction} from "./CorpusFormatActions";

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

export const loadSearchSettingsAction = (isAdmin: boolean): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockLoadSearchSettings(dispatch, isAdmin);
        return;
    }
    axios.get<Array<SearchSettings>>(`${API_BASE_PATH}/search-settings`, {withCredentials: true})
        .then(response => {
            const searchSettings = response.data;
            dispatch(searchSettingsLoadedAction(searchSettings));
            if (searchSettings.length == 0) {
                dispatch(openSnackBar('No search settings loaded'));
            }
        })
        .catch(() => {
            dispatch(openSnackBar('Could not load configurations'));
        });
}

export const updateSearchSettingsRequestAction = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockUpdateSearchSettings(dispatch, settings, onDone);
        return;
    }
    axios.put(`${API_BASE_PATH}/search-settings`, settings, {withCredentials: true})
        .then(() => {
            dispatch(searchSettingsUpdatedAction(settings))
            dispatch(openSnackBar(`Search settings ${settings.name} updated`));
            onDone();
        })
        .catch((response) => {
            const errors = response.response.data.status === 400 ? parseValidationErrors(response) : {}
            onError(errors);
            dispatch(openSnackBar(`Failed to updated ${settings.name}`));
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
                if (useMockApi()) {
                    mockUploadSettings(settings, dispatch);
                    return;
                }
                dispatch(showProgressBarAction());
                axios.post<SearchSettings>(`${API_BASE_PATH}/search-settings/import`, settings, {withCredentials: true})
                    .then((response) => {
                        dispatch(searchSettingsAddedAction(response.data))
                        dispatch(hideProgressBarAction());
                    })
                    .catch((response) => {
                        if (response.response.data.status == 400) {
                            dispatch(openSnackBar(`Could not import settings  - they are not valid`));
                            console.error(parseValidationErrors(response));
                        } else {
                            dispatch(openSnackBar(`Could not import settings ${settings.name}`));
                        }
                        dispatch(hideProgressBarAction());
                    })
            } else {
                dispatch(openSnackBar(`File ${file.name} does not contains valid settings`));
                console.error(settings);
            }
        } catch (e) {
            if (e instanceof SyntaxError) {
                dispatch(openSnackBar(`File ${file.name} does not contains valid JSON`));
            } else throw e;
        }
    });
};

export const saveNewSearchSettingsAction = (searchSettings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockSaveNewSearchSettings(dispatch, searchSettings, onDone);
        return;
    }
    axios.post<SearchSettings>(`${API_BASE_PATH}/search-settings`, searchSettings, {withCredentials: true})
        .then((response) => {
            dispatch(openSnackBar(`Search settings ${searchSettings.name} added`));
            response.data.isTransient = true; // reducer has to recognize this as a newly added search settings
            dispatch(searchSettingsUpdatedAction(response.data));
        })
        .catch((response) => {
            const errors = response.response.data.status === 400 ? parseValidationErrors(response) : {}
            onError(errors);
            dispatch(openSnackBar(`Adding search settings ${searchSettings.name} failed`));
        })
}

export const removeSearchSettingsRequestAction = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockRemoveSearchSettings(dispatch, settings, onDone);
        return;
    }
    axios.delete(`${API_BASE_PATH}/search-settings/${settings.id}`, {withCredentials: true})
        .then(() => {
            dispatch(searchSettingsRemovedAction(settings))
            dispatch(openSnackBar(`Search settings ${settings.name} removed`));
        }).catch(() => {
        dispatch(openSnackBar(`Failed to remove search settings ${settings.name}`));
        onError({})
    })
};


export const changeDefaultSearchSettingsRequestAction = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    if (useMockApi()) {
        mockChangeDefaultSearchSettings(dispatch, settings, onDone);
        return;
    }
    axios.put(`${API_BASE_PATH}/search-settings/default/${settings.id}`, null, {withCredentials: true})
        .then(() => {
            dispatch(searchSettingsNewDefaultAction(settings))
            dispatch(openSnackBar(`Search settings ${settings.name} were made default`));
            onDone();
        })
        .catch(() => {
            dispatch(openSnackBar(`Could not make ${settings.name} default`));
            onError({})
        })
};



