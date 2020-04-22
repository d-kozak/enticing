import {createSlice, PayloadAction} from "redux-starter-kit";
import {ApplicationState, UserState} from "../ApplicationState";
import {User} from "../entities/user";


const {reducer, actions} = createSlice({
    slice: 'userDetails',
    initialState: {currentUserLogin: null} as UserState,
    reducers: {
        logoutSuccessAction: (state: UserState) => {
            state.currentUserLogin = null;
        },
        loginSuccessAction: (state: UserState, action: PayloadAction<User>) => {
            state.currentUserLogin = action.payload.login;
        }
    },

});

export const isLoggedIn = (state: ApplicationState) => state.userDetails.currentUserLogin != null
export const getCurrentUserDetails = (state: ApplicationState): User | null =>
    state.userDetails.currentUserLogin !== null ? state.users.elements[state.userDetails.currentUserLogin] : null;
export const isAdmin = (state: ApplicationState) => {
    const user = getCurrentUserDetails(state);
    return user && user.roles.indexOf("ADMIN") !== -1
}
export const isMaintainer = (state: ApplicationState) => {
    const user = getCurrentUserDetails(state);
    return user && user.roles.indexOf("PLATFORM_MAINTAINER") !== -1
}

export const {loginSuccessAction, logoutSuccessAction} = actions;

export default reducer;