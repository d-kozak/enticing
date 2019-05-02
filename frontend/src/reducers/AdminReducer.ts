import {
    ADMIN_DELETE_USER_SUCCESS,
    ADMIN_USER_UPDATE_SUCCESS,
    ADMIN_USERS_LOADED,
    AdminAction
} from "../actions/AdminActions";
import {User} from "../entities/User";

export type AdminState = Readonly<typeof initialState>

const initialState = {
    users: Array<User>()
}

type AdminReducer = (state: AdminState | undefined, action: AdminAction) => AdminState

const adminReducer: AdminReducer = (state = initialState, action) => {
    switch (action.type) {
        case ADMIN_USERS_LOADED:
            return {
                users: action.users
            };
        case ADMIN_USER_UPDATE_SUCCESS:
            const updatedUsers = state.users
                .map((user) => user.login === action.user.login ? action.user : user)
            return {
                users: updatedUsers
            };
        case ADMIN_DELETE_USER_SUCCESS:
            return {
                users: state.users.filter(user => user.login != action.user.login)
            };

    }
    return state
}

export default adminReducer;