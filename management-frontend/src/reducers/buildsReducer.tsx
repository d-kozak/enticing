import {createSlice, PayloadAction} from "redux-starter-kit";
import {PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {CommandDto} from "../entities/CommandDto";
import PaginatedCollections from "../entities/PaginatedCollections";

const {reducer, actions} = createSlice({
    slice: 'builds',
    initialState: PaginatedCollections.emptyCollection<CommandDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<CommandDto>, actions: PayloadAction<PaginatedResult<CommandDto>>) => {
            PaginatedCollections.addAll(state, actions.payload, {
                stringifyId: true
            })
        },
        clearAll: (state: PaginatedCollection<CommandDto>) => {
            PaginatedCollections.clear(state);
        }
    }
});

export const {addNewItems, clearAll} = actions;

export default reducer;