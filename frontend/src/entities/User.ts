import {UserSettings} from "./UserSettings";

export interface User {
    id: number,
    login: string,
    isActive: boolean,
    roles: Set<String>,
    selectedSettings: number | null,
    userSettings: UserSettings
}