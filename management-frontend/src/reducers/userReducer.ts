import {createSlice, PayloadAction} from "redux-starter-kit";
import {UserState} from "../ApplicationState";
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

export const {loginSuccessAction, logoutSuccessAction} = actions;

export default reducer;