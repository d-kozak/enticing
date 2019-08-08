import {createSelector} from "reselect";
import {ApplicationState} from "../ApplicationState";

export const isUserAdmin = (state: ApplicationState) => {
    if (state.userState.user !== null) {
        return state.userState.user.roles.indexOf("ADMIN") != -1;
    }
    return false;
};

export const isLoggedIn = (state: ApplicationState) => state.userState.user !== null;

const getSearchSettings = (state: ApplicationState) => state.searchSettings.settings;

const getUserSearchSettings = (state: ApplicationState) => state.userState.user !== null ? state.userState.user.selectedSettings : state.userState.selectedSettings;

const getSelectedMetadata = (state: ApplicationState) => state.userState.user !== null ? state.userState.user.selectedMetadata : state.userState.selectedMetadata;

export const getSelectedSearchSettings = createSelector(getSearchSettings, getUserSearchSettings, (searchSettings, selectedSettings) => {
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
    console.warn(JSON.stringify(searchSettings, null, 2));
    console.warn(JSON.stringify(selectedSettings, null, 2));
    return null
});


export const getNewSearchSettings = createSelector(getSearchSettings,
    (searchSettings) => Object.values(searchSettings).find(item => item.isTransient == true));