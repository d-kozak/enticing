import {UserSettings} from "../entities/UserSettings";

export const USER_SETTINGS_LOADED = "[USER SETTINGS] LOADED";
export const USER_SETTINGS_UPDATED = "[USER SETTINGS] UPDATED";

interface UserSettingsLoadedAction {
    type: typeof USER_SETTINGS_LOADED,
    settings: UserSettings
}

interface UserSettingsUpdatedAction {
    type: typeof USER_SETTINGS_UPDATED,
    settings: UserSettings
}

export type UserSettingsAction = UserSettingsLoadedAction | UserSettingsUpdatedAction;


export const userSettingsLoadedAction = (settings: UserSettings): UserSettingsLoadedAction => ({
    type: USER_SETTINGS_LOADED,
    settings
});

export const userSettingsUpdatedAction = (settings: UserSettings): UserSettingsUpdatedAction => ({
    type: USER_SETTINGS_UPDATED,
    settings
});



