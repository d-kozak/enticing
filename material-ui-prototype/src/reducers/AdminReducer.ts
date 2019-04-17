import {AdminState} from "../AppState";
import {AdminAction} from "../actions/AdminActions";


type AdminReducer = (state: AdminState | undefined, action: AdminAction) => AdminState


const initialState: AdminState = {
    users: []
}

const adminReducer: AdminReducer = (state = initialState, action) => {
    switch (action.type) {
        case "[ADMIN] USERS LOADED":
            return {
                users: action.users
            };
        case "[ADMIN] UPDATE USER SUCCESS":
            const updatedUsers = state.users
                .map((user) => user.login === action.user.login ? action.user : user)
            return {
                users: updatedUsers
            };
        case "[ADMIN] DELETE USER SUCCESS":
            return {
                users: state.users.filter(user => user.login != action.user.login)
            };

    }
    return state
}

export default adminReducer;