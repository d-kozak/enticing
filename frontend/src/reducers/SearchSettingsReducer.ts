import {
    SEARCH_SETTINGS_ADDED,
    SEARCH_SETTINGS_LOADED,
    SEARCH_SETTINGS_NEW_DEFAULT,
    SEARCH_SETTINGS_REMOVED,
    SEARCH_SETTINGS_UPDATED,
    SearchSettingsAction
} from "../actions/SearchSettingsActions";
import {SearchSettings} from "../entities/SearchSettings";

const initialState = {
    settings: [] as Array<SearchSettings>
};

type SearchSettingsState = Readonly<typeof initialState>;

type SearchSettingsReducer = (state: SearchSettingsState | undefined, action: SearchSettingsAction) => SearchSettingsState;

const searchSettingsReducer: SearchSettingsReducer = (state = initialState, action) => {
    switch (action.type) {
        case SEARCH_SETTINGS_LOADED:
            return {
                settings: action.settings
            };
        case SEARCH_SETTINGS_ADDED:
            return {
                settings: [...state.settings, action.settings]
            };
        case SEARCH_SETTINGS_UPDATED:
            return {
                settings: state.settings.map((item => item.id === action.settings.id ? action.settings : item))
            };
        case SEARCH_SETTINGS_REMOVED:
            return {
                settings: state.settings.filter(item => item.id != action.settings.id)
            };
        case SEARCH_SETTINGS_NEW_DEFAULT:
            return {
                settings: state.settings.map(item => ({...item, default: item.id === action.settings.id}))
            }
    }
    return state;
};

export default searchSettingsReducer;