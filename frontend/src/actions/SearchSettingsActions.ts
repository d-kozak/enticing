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
import {useMockApi} from "../globals";
import {openSnackBar} from "./SnackBarActions";

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
    | SearchSettingsAddingCancelledAction;

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
    mockLoadSearchSettings(dispatch, isAdmin)
}

export const updateSearchSettingsRequestAction = (newSettings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    mockUpdateSearchSettings(dispatch, newSettings, onDone);
};

export const addEmptySearchSettingsRequestAction = (): ThunkResult<void> => (dispatch) => {
    const newSettings: SearchSettings = {
        id: 0,
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
                    mockUploadSettings(settings, dispatch)
                }
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
    mockSaveNewSearchSettings(dispatch, searchSettings, onDone);
}

export const removeSearchSettingsRequestAction = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    mockRemoveSearchSettings(dispatch, settings, onDone);
};


export const changeDefaultSearchSettingsRequestAction = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    mockChangeDefaultSearchSettings(dispatch, settings, onDone);
};



