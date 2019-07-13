import {
    USER_LOGIN_SUCCESS,
    USER_LOGOUT,
    USER_SEARCH_SETTINGS_SELECTED_SUCCESS,
    USER_SETTINGS_UPDATED,
    UserAction
} from "../actions/UserActions";
import {User} from "../entities/User";

const initialState = {
    user: null as User | null,
    selectedSettings: null as string | null
}

export type UserState = Readonly<typeof initialState>

type UserReducer = (state: UserState | undefined, action: UserAction) => UserState

const userReducer: UserReducer = (state = initialState, action) => {
    switch (action.type) {
        case USER_LOGIN_SUCCESS:
            return {
                user: action.user,
                selectedSettings: action.user.selectedSettings
            }
        case USER_LOGOUT:
            return {user: null, selectedSettings: null}
        case USER_SETTINGS_UPDATED: {
            if (!state.user) {
                throw new Error("Cannot update settings when no user is logged in")
            }
            return {
                ...state,
                user: {
                    ...state.user,
                    userSettings: action.userSettings
                }
            }
        }
        case USER_SEARCH_SETTINGS_SELECTED_SUCCESS:
            return {
                ...state,
                selectedSettings: typeof action.settings === 'string' ? action.settings : action.settings.id
            };
    }
    return state
}


export default userReducer;