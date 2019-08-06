import {
    ADMIN_DELETE_USER_SUCCESS,
    ADMIN_USER_CREATE_SUCCESS,
    ADMIN_USER_UPDATE_SUCCESS,
    ADMIN_USERS_LOADED,
    AdminAction
} from "../actions/AdminActions";
import {AdminState, initialState} from "./ApplicationState";


type AdminReducer = (state: AdminState | undefined, action: AdminAction) => AdminState

const adminReducer: AdminReducer = (state = initialState.adminState, action) => {
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

        case ADMIN_USER_CREATE_SUCCESS:
            return {
                users: [...state.users, action.user]
            }

    }
    return state
}

export default adminReducer;