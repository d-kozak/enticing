import {createSlice, PayloadAction} from "redux-starter-kit";
import {clearCollection, emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {LogDto} from "../entities/LogDto";
import {generate} from "shortid";


const {reducer, actions} = createSlice({
    slice: 'logs',
    initialState: emptyPaginatedCollection<LogDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<LogDto>, actions: PayloadAction<PaginatedResult<LogDto>>) => {
            const payload = actions.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = generate();
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            state.totalElements = payload.totalElements
        },
        clearAll: (state: PaginatedCollection<LogDto>) => {
            clearCollection(state);
        }
    }
});

export const {addNewItems, clearAll} = actions;

export default reducer;