import {createSelector} from "reselect";
import {ApplicationState} from "../ApplicationState";


const getSearchSettings = (state: ApplicationState) => state.searchSettings.settings;

const getUserSearchSettings = (state: ApplicationState) => state.userState.user.selectedSettings;

const getSelectedMetadata = (state: ApplicationState) => state.userState.user.selectedMetadata;

export const getSelectedSearchSettings = createSelector(getSearchSettings, getUserSearchSettings, (searchSettings, selectedSettings) => {
    if (Object.keys(searchSettings).length == 0) {
        console.warn("no search settings available");
        return null;
    }
    if (selectedSettings != null) {
        for (let settings of Object.values(searchSettings)) {
            if (settings.id == selectedSettings) {
                return settings;
            }
        }
    }
    for (let settings of Object.values(searchSettings)) {
        if (settings.default) {
            return settings;
        }
    }
    // if nothing is selected, just return the first one
    return Object.values(searchSettings)[0];
});


export const getNewSearchSettings = createSelector(getSearchSettings,
    (searchSettings) => Object.values(searchSettings).find(item => item.isTransient == true));


export const getSelectedMetadataForCurrentSettings = createSelector(getSelectedSearchSettings, getSelectedMetadata, (selectedSearchSettings, selectedMetadata) => {
    if (selectedSearchSettings == null) return null;
    const metadata = selectedMetadata[selectedSearchSettings.id];
    if (!metadata) return null;
    return metadata;
});