import {UserSettings} from "./UserSettings";
import {SelectedMetadata} from "./SelectedMetadata";

export interface User {
    id: number,
    login: string,
    active: boolean,
    roles: Array<String>,
    userSettings: UserSettings,
    selectedSettings: string | null,
    selectedMetadata: { [key: string]: SelectedMetadata }
}

export const createAnonymousUser: () => User = () => ({
    id: 0,
    active: false,
    login: "",
    roles: [],
    userSettings: {
        resultsPerPage: 20
    },
    selectedSettings: null,
    selectedMetadata: {}
});