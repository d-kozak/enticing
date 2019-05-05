import {SearchSettings} from "../entities/SearchSettings";

export const selectedSearchSettingsIndex = (searchSettings: Array<SearchSettings>, selectedSettings: number | null): number => {
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
};