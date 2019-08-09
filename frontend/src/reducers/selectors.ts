import {createSelector} from "reselect";
import {ApplicationState} from "../ApplicationState";


const getSearchSettings = (state: ApplicationState) => state.searchSettings.settings;

const getUserSearchSettings = (state: ApplicationState) => state.userState.user.selectedSettings;

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