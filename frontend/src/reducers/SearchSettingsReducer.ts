import {
    SEARCH_SETTINGS_ADDED,
    SEARCH_SETTINGS_ADDING_CANCELLED,
    SEARCH_SETTINGS_LOADED,
    SEARCH_SETTINGS_NEW_DEFAULT,
    SEARCH_SETTINGS_REMOVED,
    SEARCH_SETTINGS_UPDATED,
    SearchSettingsAction
} from "../actions/SearchSettingsActions";
import {SearchSettings} from "../entities/SearchSettings";
import {mapValues} from 'lodash';

const initialState = {
    settings: {} as { [key: string]: SearchSettings }
};

type SearchSettingsState = Readonly<typeof initialState>;

type SearchSettingsReducer = (state: SearchSettingsState | undefined, action: SearchSettingsAction) => SearchSettingsState;

const searchSettingsReducer: SearchSettingsReducer = (state = initialState, action) => {
    switch (action.type) {
        case SEARCH_SETTINGS_LOADED:
            return {
                settings: action.settings.reduce((obj, item) => {
                    obj[item.id] = item;
                    return obj;
                }, {} as { [key: string]: SearchSettings })
            };
        case SEARCH_SETTINGS_ADDED:
            if (action.settings.isTransient && Object.values(state.settings).find(setting => setting.isTransient == true)) {
                throw new Error("Cannot create second transient settings.")
            }
            return {
                settings: {
                    ...state.settings,
                    "transient": action.settings
                }
            };
        case SEARCH_SETTINGS_UPDATED:
            if (action.settings.isTransient) {
                const newSettings: SearchSettings = {
                    ...action.settings,
                    isTransient: false
                }
                return {
                    settings: mapValues(state.settings, item => item.id === action.settings.id ? newSettings : item)
                }
            }
            return {
                settings: mapValues(state.settings, item => item.id === action.settings.id ? action.settings : item)
            };

        case SEARCH_SETTINGS_REMOVED: {
            return {
                settings: Object.values(state.settings)
                    .filter(item => item.id == action.settings.id)
                    .reduce((obj, item) => {
                        obj[item.id] = item;
                        return obj;
                    }, {} as { [key: string]: SearchSettings })
            };
        }
        case SEARCH_SETTINGS_NEW_DEFAULT: {
            return {
                settings: mapValues(state.settings, item => ({...item, default: item.id === action.settings.id}))
            }
        }
        case SEARCH_SETTINGS_ADDING_CANCELLED:
            return {
                settings: Object.values(state.settings)
                    .filter(item => !item.isTransient)
                    .reduce((obj, item) => {
                        obj[item.id] = item;
                        return obj;
                    }, {} as { [key: string]: SearchSettings })
            }
    }
    return state;
};

export default searchSettingsReducer;