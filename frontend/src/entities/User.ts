export interface User {
    id: number,
    login: string,
    isActive: boolean,
    isAdmin: boolean,
    selectedSettings: number | null
}