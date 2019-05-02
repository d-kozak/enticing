import {USER_LOGIN_SUCCESS, USER_LOGOUT, UserAction} from "../actions/UserActions";

const initialState = {
    isLoggedIn: false,
    isAdmin: false
}

export type UserState = Readonly<typeof initialState>

type UserReducer = (state: UserState | undefined, action: UserAction) => UserState

const userReducer: UserReducer = (state = initialState, action) => {
    switch (action.type) {
        case USER_LOGIN_SUCCESS:
            return {
                isLoggedIn: true,
                isAdmin: action.isAdmin
            }

        case USER_LOGOUT:
            return {
                isLoggedIn: false,
                isAdmin: false
            }
    }
    return state
}


export default userReducer;