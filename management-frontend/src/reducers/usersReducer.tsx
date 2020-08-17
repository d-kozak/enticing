import {createSlice, PayloadAction} from "redux-starter-kit";
import {PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {User} from "../entities/user";
import {loginSuccessAction} from "./userDetailsReducer";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";
import PaginatedCollections from "../entities/PaginatedCollections";


const {reducer, actions} = createSlice({
    slice: 'users',
    initialState: PaginatedCollections.emptyCollection<User>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<User>, actions: PayloadAction<PaginatedResult<User>>) => {
            PaginatedCollections.addAll(state, actions.payload, {
                forEachElem: (elem) => {
                    elem.id = elem.login;
                }
            })
        },
        addUser: (state: PaginatedCollection<User>, action: PayloadAction<User>) => {
            PaginatedCollections.add(state, action.payload, {
                forEachElem: elem => elem.id = elem.login
            })
        },
        clearAll: (state: PaginatedCollection<User>) => {
            PaginatedCollections.clear(state);
        }
    },
    extraReducers: {
        [loginSuccessAction.type]: (state: PaginatedCollection<User>, action: PayloadAction<User>) => {
            PaginatedCollections.add(state, action.payload, {
                forEachElem: elem => elem.id = elem.login
            })
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