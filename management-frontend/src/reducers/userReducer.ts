import {createSlice, PayloadAction} from "redux-starter-kit";
import {ApplicationState, UserState} from "../ApplicationState";
import {User} from "../entities/user";

const {reducer, actions} = createSlice({
    slice: 'user',
    initialState: {currentUser: null} as UserState,
    reducers: {
        loginSuccessAction: (state: UserState, action: PayloadAction<User>) => {
            state.currentUser = action.payload;
        },
        logoutSuccessAction: (state: UserState) => {
            state.currentUser = null;
        }
    }
});

export const isAdmin = (state: ApplicationState) => state.userState.currentUser && state.userState.currentUser.roles.indexOf("ADMIN") !== -1
export const isMaintainer = (state: ApplicationState) => state.userState.currentUser && state.userState.currentUser.roles.indexOf("PLATFORM_MAINTAINER") !== -1
export const isLoggedIn = (state: ApplicationState) => state.userState.currentUser !== null;
export const getUser = (state: ApplicationState) => state.userState.currentUser;

export const {loginSuccessAction, logoutSuccessAction} = actions;

export default reducer;