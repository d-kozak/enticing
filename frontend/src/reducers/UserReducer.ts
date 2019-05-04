import {
    USER_LOGIN_SUCCESS,
    USER_LOGOUT,
    USER_SEARCH_SETTINGS_SELECTED_SUCCESS,
    UserAction
} from "../actions/UserActions";

const initialState = {
    isLoggedIn: false,
    isAdmin: false,
    selectedSettings: null as number | null
}

export type UserState = Readonly<typeof initialState>

type UserReducer = (state: UserState | undefined, action: UserAction) => UserState

const userReducer: UserReducer = (state = initialState, action) => {
    switch (action.type) {
        case USER_LOGIN_SUCCESS:
            return {
                ...state,
                isLoggedIn: true,
                isAdmin: action.isAdmin
            }

        case USER_LOGOUT:
            return {
                ...state,
                isLoggedIn: false,
                isAdmin: false
            }
        case USER_SEARCH_SETTINGS_SELECTED_SUCCESS:
            return {
                ...state,
                selectedSettings: action.settings.id
            };
    }
    return state
}


export default userReducer;