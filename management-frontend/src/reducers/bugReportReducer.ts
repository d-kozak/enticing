import {createSlice, PayloadAction} from "redux-starter-kit";
import {clearCollection, emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {generate} from "shortid";
import {BugReport} from "../entities/BugReport";


const {reducer, actions} = createSlice({
    slice: 'logs',
    initialState: emptyPaginatedCollection<BugReport>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<BugReport>, actions: PayloadAction<PaginatedResult<BugReport>>) => {
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
        clearAll: (state: PaginatedCollection<BugReport>) => {
            clearCollection(state);
        }
    }
});

export const {addNewItems, clearAll} = actions;

export default reducer;