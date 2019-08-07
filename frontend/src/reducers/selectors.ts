import {createSelector} from "reselect";
import {ApplicationState} from "./ApplicationState";

export const isAdminSelector = (state: ApplicationState) => {
    if (state.userState.user !== null) {
        return state.userState.user.roles.indexOf("ADMIN") != -1;
    }
    return false;
}

export const isLoggedInSelector = (state: ApplicationState) => state.userState.user !== null

const searchSettingsSelector = (state: ApplicationState) => state.searchSettings.settings;

const selectedSettingsSelector = (state: ApplicationState) => state.userState.selectedSettings

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