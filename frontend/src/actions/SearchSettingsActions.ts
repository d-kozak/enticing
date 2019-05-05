import {SearchSettings} from "../entities/SearchSettings";
import {ThunkResult} from "./RootActions";
import {
    mockAddSearchSettings,
    mockChangeDefaultSearchSettings,
    mockLoadSearchSettings,
    mockRemoveSearchSettings,
    mockUpdateSearchSettings
} from "../mocks/mockSearchSettings";

export const SEARCH_SETTINGS_LOADED = "[SEARCH SETTINGS] LOADED";
export const SEARCH_SETTINGS_ADDED = "[SEARCH SETTINGS] ADDED";
export const SEARCH_SETTINGS_UPDATED = "[SEARCH SETTINGS] UPDATED";
export const SEARCH_SETTINGS_REMOVED = "[SEARCH SETTINGS] REMOVED";
export const SEARCH_SETTINGS_NEW_DEFAULT = "[SEARCH SETTINGS] NEW DEFAULT";

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

export type SearchSettingsAction =
    SearchSettingsLoadedAction
    | SearchSettingsAddedAction
    | SearchSettingsUpdatedAction
    | SearchSettingsRemovedAction
    | SearchSettingsNewDefaultAction;

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

export const loadSearchSettingsAction = (isAdmin: boolean): ThunkResult<void> => (dispatch) => {
    mockLoadSearchSettings(dispatch, isAdmin)
}

export const updateSearchSettingsRequestAction = (newSettings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    mockUpdateSearchSettings(dispatch, newSettings, onDone);
};

export const addSearchSettingsRequestAction = (): ThunkResult<void> => (dispatch) => {
    mockAddSearchSettings(dispatch);
};

export const removeSearchSettingsRequestAction = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    mockRemoveSearchSettings(dispatch, settings, onDone);
};


export const changeDefaultSearchSettingsRequestAction = (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void): ThunkResult<void> => (dispatch) => {
    mockChangeDefaultSearchSettings(dispatch, settings, onDone);
};



