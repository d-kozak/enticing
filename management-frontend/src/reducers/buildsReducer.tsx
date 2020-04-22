import {createSlice, PayloadAction} from "redux-starter-kit";
import {emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {CommandDto} from "../entities/CommandDto";

const {reducer, actions} = createSlice({
    slice: 'builds',
    initialState: emptyPaginatedCollection<CommandDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<CommandDto>, actions: PayloadAction<PaginatedResult<CommandDto>>) => {
            const payload = actions.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = elem.id.toString(); // (in case it was parsed as a number, transform it back to string)
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            state.totalElements = payload.totalElements;
        }
    }
});

export const {addNewItems} = actions;

export default reducer;