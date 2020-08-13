import {createSlice, PayloadAction} from "redux-starter-kit";
import {
    addNewItemsToCollection,
    clearCollection,
    emptyPaginatedCollection,
    PaginatedCollection,
    PaginatedResult
} from "../entities/pagination";
import {LogDto} from "../entities/LogDto";


const {reducer, actions} = createSlice({
    slice: 'logs',
    initialState: emptyPaginatedCollection<LogDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<LogDto>, actions: PayloadAction<PaginatedResult<LogDto>>) => {
            addNewItemsToCollection(state, actions.payload, {generateId: true})
        },
        clearAll: (state: PaginatedCollection<LogDto>) => {
            clearCollection(state);
        }
    }
});

export const {addNewItems, clearAll} = actions;

export default reducer;