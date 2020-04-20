import {createSlice, PayloadAction} from "redux-starter-kit";
import {emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {LogDto} from "../entities/LogDto";
import {generate} from "shortid";


const {reducer, actions} = createSlice({
    slice: 'logs',
    initialState: emptyPaginatedCollection<LogDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<LogDto>, actions: PayloadAction<PaginatedResult<LogDto>>) => {
            const payload = actions.payload;
            for (let item of payload.content)
                item.id = generate();
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++)
                state.content[offset + i] = payload.content[i];
            state.totalElements = payload.totalElements
        }
    }
});

export const {addNewItems} = actions;

export default reducer;