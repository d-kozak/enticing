import {createSelector} from "reselect";
import {AppState} from "./RootReducer";

export const isAdminSelector = (state: AppState) => {
    if (state.userState.user !== null) {
        return state.userState.user.roles.indexOf("ADMIN") != -1;
    }
    return false;
}

export const isLoggedInSelector = (state: AppState) => state.userState.user !== null

const searchSettingsSelector = (state: AppState) => state.searchSettings.settings;

const selectedSettingsSelector = (state: AppState) => state.userState.selectedSettings

export const selectedSearchSettingsIndexSelector = createSelector(searchSettingsSelector, selectedSettingsSelector, (searchSettings, selectedSettings) => {
    if (selectedSettings == null) {
        for (let i in searchSettings) {
            if (searchSettings[i].default) {
                return Number(i)
            }
        }
        return 0
    }
    for (let i in searchSettings) {
        if (searchSettings[i].id == selectedSettings) {
            return Number(i)
        }
    }
    return 0
});
