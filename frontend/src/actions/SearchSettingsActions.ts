import {SearchSettings} from "../entities/SearchSettings";
import {ThunkResult} from "./RootActions";
import {mockLoadSettings} from "../mocks/mockSearchSettings";

export const SEARCH_SETTINGS_LOADED = "[SEARCH SETTINGS] LOADED";
export const SEARCH_SETTINGS_ADDED = "[SEARCH SETTINGS] ADDED";
export const SEARCH_SETTINGS_UPDATED = "[SEARCH SETTINGS] UPDATED";
export const SEARCH_SETTINGS_REMOVED = "[SEARCH SETTINGS] REMOVED";

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

export type SearchSettingsAction =
    SearchSettingsLoadedAction
    | SearchSettingsAddedAction
    | SearchSettingsUpdatedAction
    | SearchSettingsRemovedAction;

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

export const loadSearchSettingsAction = (): ThunkResult<void> => (dispatch) => {
    mockLoadSettings(dispatch)
}

