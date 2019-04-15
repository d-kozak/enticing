import {UserState} from "../AppState";
import {UserAction} from "../actions/UserActions";


type UserReducer = (state: UserState | undefined, action: UserAction) => UserState

const initialState: UserState = {
    isLoggedIn: false,
    isAdmin: false
}

const userReducer: UserReducer = (state = initialState, action) => {
    return state
}


export default userReducer;