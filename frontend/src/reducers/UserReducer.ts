import {UserState} from "../AppState";
import {USER_LOGIN_SUCCESS, USER_LOGOUT, UserAction} from "../actions/UserActions";


type UserReducer = (state: UserState | undefined, action: UserAction) => UserState

const initialState: UserState = {
    isLoggedIn: false,
    isAdmin: false
}

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