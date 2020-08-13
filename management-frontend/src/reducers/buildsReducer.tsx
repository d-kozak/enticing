import {createSlice, PayloadAction} from "redux-starter-kit";
import {
    addNewItemsToCollection,
    clearCollection,
    emptyPaginatedCollection,
    PaginatedCollection,
    PaginatedResult
} from "../entities/pagination";
import {CommandDto} from "../entities/CommandDto";

const {reducer, actions} = createSlice({
    slice: 'builds',
    initialState: emptyPaginatedCollection<CommandDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<CommandDto>, actions: PayloadAction<PaginatedResult<CommandDto>>) => {
            addNewItemsToCollection(state, actions.payload, {
                stringifyId: true
            })
        },
        clearAll: (state: PaginatedCollection<CommandDto>) => {
            clearCollection(state);
        }
    }
});

export const {addNewItems, clearAll} = actions;

export default reducer;