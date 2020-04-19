export interface User {
    login: string,
    active: boolean,
    roles: Array<string>
}

export function isMaintainer(user: User): boolean {
    return user.roles.indexOf("PLATFORM_MAINTAINER") != -1
}

export function isAdmin(user: User): boolean {
    return user.roles.indexOf("ADMIN") != -1
}

export interface UserCredentials {
    login: string
    password: string
}

export interface ChangePasswordCredentials {
    login: string,
    oldPassword: string,
    newPassword: string
}

export interface CreateUserRequest {
    login: string,
    password: string,
    roles: Array<string>
}
