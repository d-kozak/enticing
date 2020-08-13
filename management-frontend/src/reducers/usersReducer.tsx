import {createSlice, PayloadAction} from "redux-starter-kit";
import {
    addNewItemsToCollection,
    clearCollection,
    emptyPaginatedCollection,
    PaginatedCollection,
    PaginatedResult
} from "../entities/pagination";
import {User} from "../entities/user";
import {loginSuccessAction} from "./userDetailsReducer";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";


const {reducer, actions} = createSlice({
    slice: 'users',
    initialState: emptyPaginatedCollection<User>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<User>, actions: PayloadAction<PaginatedResult<User>>) => {
            addNewItemsToCollection(state, actions.payload, {
                forEachElem: (elem) => {
                    elem.id = elem.login;
                }
            })
        },
        addUser: (state: PaginatedCollection<User>, action: PayloadAction<User>) => {
            const user = action.payload;
            user.id = user.login
            state.elements[user.id] = user;
        }
        ,
        clearAll: (state: PaginatedCollection<User>) => {
            clearCollection(state);
        }
    },
    extraReducers: {
        [loginSuccessAction.type]: (state: PaginatedCollection<User>, action: PayloadAction<User>) => {
            const user = action.payload;
            user.id = user.login;
            state.elements[user.id] = user;
        }
    }
});

export const {addNewItems, addUser, clearAll} = actions;

export default reducer;

export const requestUserInfo = (id: string): ThunkResult<void> => async (dispatch) => {
    try {
        const user = await getRequest<User>(`/user/details/${id}`);
        dispatch(addUser(user));
    } catch (e) {
        console.error(e);
    }
}