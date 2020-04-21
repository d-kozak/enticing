import {createSlice, PayloadAction} from "redux-starter-kit";
import {emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {generate} from "shortid";
import {PerfDto} from "../entities/PerfDto";


const {reducer, actions} = createSlice({
    slice: 'perfLogs',
    initialState: emptyPaginatedCollection<PerfDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<PerfDto>, actions: PayloadAction<PaginatedResult<PerfDto>>) => {
            const payload = actions.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = generate();
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            state.totalElements = payload.totalElements
        }
    }
});

export const {addNewItems} = actions;

export default reducer;