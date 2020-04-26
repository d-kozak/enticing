export interface User {
    // todo id
    id: string
    login: string,
    roles: Array<string>
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
