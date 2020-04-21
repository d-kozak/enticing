import {createSlice, PayloadAction} from "redux-starter-kit";
import {emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {User} from "../entities/user";


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
        }
    }
});

export const {addNewItems} = actions;

export default reducer;