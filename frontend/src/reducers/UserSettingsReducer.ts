import {USER_SETTINGS_LOADED, USER_SETTINGS_UPDATED, UserSettingsAction} from "../actions/UserSettingsActions";
import {UserSettings} from "../entities/UserSettings";

const initialState: UserSettings = {
    resultsPerPage: 20
};

type UserSettingsState = Readonly<typeof initialState>;

type UserSettingsReducer = (state: UserSettingsState | undefined, action: UserSettingsAction) => UserSettingsState;

const userSettingsReducer: UserSettingsReducer = (state = initialState, action) => {
    switch (action.type) {
        case USER_SETTINGS_LOADED:
            return {
                ...action.settings
            };
        case USER_SETTINGS_UPDATED:
            return {
                ...action.settings
            }
    }
    return state;
};

export default userSettingsReducer;