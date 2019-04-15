type LogoutAction = {
    type: "[USER] LOGOUT"
}

export type UserAction = LogoutAction

export const logoutAction = (): LogoutAction => ({type: "[USER] LOGOUT"});