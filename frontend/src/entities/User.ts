import {UserSettings} from "./UserSettings";

export interface User {
    id: number,
    login: string,
    isActive: boolean,
    roles: Array<String>,
    selectedSettings: number | null,
    userSettings: UserSettings
}