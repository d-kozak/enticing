import {UserSettings} from "./UserSettings";
import {SelectedMetadata} from "./SelectedMetadata";

export interface User {
    id: number,
    login: string,
    active: boolean,
    roles: Array<String>,
    selectedSettings: string | null,
    selectedMetadata: SelectedMetadata | null,
    userSettings: UserSettings
}