import {createSlice, PayloadAction} from "redux-starter-kit";
import {PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {PerfDto} from "../entities/PerfDto";
import PaginatedCollections from "../entities/PaginatedCollections";


const {reducer, actions} = createSlice({
    slice: 'perfLogs',
    initialState: PaginatedCollections.emptyCollection<PerfDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<PerfDto>, actions: PayloadAction<PaginatedResult<PerfDto>>) => {
            PaginatedCollections.addAll(state, actions.payload, {
                generateId: true
            })
        },
        clearAll: (state: PaginatedCollection<PerfDto>) => {
            PaginatedCollections.clear(state);
        }
    }
});

export const {addNewItems, clearAll} = actions;

export default reducer;