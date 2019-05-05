import {
    USER_LOGIN_SUCCESS,
    USER_LOGOUT,
    USER_SEARCH_SETTINGS_SELECTED_SUCCESS,
    UserAction
} from "../actions/UserActions";
import {User} from "../entities/User";

const initialState = {
    user: null as User | null
}

export type UserState = Readonly<typeof initialState>

type UserReducer = (state: UserState | undefined, action: UserAction) => UserState

const userReducer: UserReducer = (state = initialState, action) => {
    switch (action.type) {
        case USER_LOGIN_SUCCESS:
            return {
                user: action.user
            }
        case USER_LOGOUT:
            return {user: null}
        case USER_SEARCH_SETTINGS_SELECTED_SUCCESS:
            return {
                ...state,
                selectedSettings: action.settings.id
            };
    }
    return state
}


export default userReducer;