import {createSlice, PayloadAction} from "redux-starter-kit";
import {
    addNewItemsToCollection,
    clearCollection,
    emptyPaginatedCollection,
    PaginatedCollection,
    PaginatedResult
} from "../entities/pagination";
import {PerfDto} from "../entities/PerfDto";


const {reducer, actions} = createSlice({
    slice: 'perfLogs',
    initialState: emptyPaginatedCollection<PerfDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<PerfDto>, actions: PayloadAction<PaginatedResult<PerfDto>>) => {
            addNewItemsToCollection(state, actions.payload, {
                generateId: true
            })
        },
        clearAll: (state: PaginatedCollection<PerfDto>) => {
            clearCollection(state);
        }
    }
});

export const {addNewItems, clearAll} = actions;

export default reducer;