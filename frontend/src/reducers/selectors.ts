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

export const selectedSearchSettingsSelector = createSelector(searchSettingsSelector, selectedSettingsSelector, (searchSettings, selectedSettings) => {
    if (selectedSettings == null) {
        for (let i in searchSettings) {
            if (searchSettings[i].default) {
                return searchSettings[i];
            }
        }
    } else {
        for (let i in searchSettings) {
            if (searchSettings[i].id == selectedSettings) {
                return searchSettings[i];
            }
        }
    }
    console.warn("Unknown settings");
    console.warn(JSON.stringify(searchSettings, null, 2))
    console.warn(JSON.stringify(selectedSettings, null, 2))
    return null
});



export const newSearchSettingsSelector = createSelector(searchSettingsSelector,
    (searchSettings) => Object.values(searchSettings).find(item => item.isTransient == true))