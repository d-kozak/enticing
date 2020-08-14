import {createSlice, PayloadAction} from "redux-starter-kit";
import {PaginatedCollection, PaginatedCollections, PaginatedResult} from "../entities/pagination";
import {LogDto} from "../entities/LogDto";


const {reducer, actions} = createSlice({
    slice: 'logs',
    initialState: PaginatedCollections.emptyCollection<LogDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<LogDto>, actions: PayloadAction<PaginatedResult<LogDto>>) => {
            PaginatedCollections.addAll(state, actions.payload, {generateId: true})
        },
        clearAll: (state: PaginatedCollection<LogDto>) => {
            PaginatedCollections.clear(state);
        }
    }
});

export const {addNewItems, clearAll} = actions;

export default reducer;