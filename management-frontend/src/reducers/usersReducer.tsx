import {createSlice, PayloadAction} from "redux-starter-kit";
import {emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {User} from "../entities/user";
import {loginSuccessAction} from "./userDetailsReducer";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";


const {reducer, actions} = createSlice({
    slice: 'users',
    initialState: emptyPaginatedCollection<User>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<User>, actions: PayloadAction<PaginatedResult<User>>) => {
            const payload = actions.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = elem.login;
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            state.totalElements = payload.totalElements
        },
        addUser: (state: PaginatedCollection<User>, action: PayloadAction<User>) => {
            const user = action.payload;
            user.id = user.login
            state.elements[user.id] = user;
        },
    },
    extraReducers: {
        [loginSuccessAction.type]: (state: PaginatedCollection<User>, action: PayloadAction<User>) => {
            const user = action.payload;
            user.id = user.login;
            state.elements[user.id] = user;
        }
    }
});

export const {addNewItems, addUser} = actions;

export default reducer;

export const requestUserInfo = (id: string): ThunkResult<void> => async (dispatch) => {
    try {
        const user = await getRequest<User>(`/user/details/${id}`);
        dispatch(addUser(user));
    } catch (e) {
        console.error(e);
    }
}