import {UserSettings} from "./UserSettings";

export interface User {
    id: number,
    login: string,
    active: boolean,
    roles: Array<String>,
    selectedSettings: string | null,
    userSettings: UserSettings
}