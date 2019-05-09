import {UserSettings} from "./UserSettings";

export interface User {
    id: number,
    login: string,
    active: boolean,
    roles: Array<String>,
    selectedSettings: number | null,
    userSettings: UserSettings
}