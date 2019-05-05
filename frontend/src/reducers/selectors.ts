import {createSelector} from "reselect";
import {AppState} from "./RootReducer";

const searchSettingsSelector = (state: AppState) => state.searchSettings.settings;
const selectedSettingsSelector = (state: AppState) => state.user.selectedSettings;

export const selectedSearchSettingsIndexSelector = createSelector(searchSettingsSelector, selectedSettingsSelector, (searchSettings, selectedSettings) => {
    if (selectedSettings == null) {
        for (let i in searchSettings) {
            if (searchSettings[i].isDefault) {
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
