import {createSlice, PayloadAction} from "redux-starter-kit";
import {clearCollection, emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {BugReport} from "../entities/BugReport";


const {reducer, actions} = createSlice({
    slice: 'bugReports',
    initialState: emptyPaginatedCollection<BugReport>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<BugReport>, actions: PayloadAction<PaginatedResult<BugReport>>) => {
            const payload = actions.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = elem.id.toString(); // (in case it was parsed as a number, transform it back to string)
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            for (let i = payload.content.length; i < payload.size; i++) {
                delete state.index[offset + i]
            }
            state.totalElements = payload.totalElements
        },
        removeReport: (state: PaginatedCollection<BugReport>, action: PayloadAction<BugReport>) => {
            const report = action.payload;
            delete state.elements[report.id];
            state.totalElements--;
        },
        clearAll: (state: PaginatedCollection<BugReport>) => {
            clearCollection(state);
        }
    }
});

export const {addNewItems, removeReport, clearAll} = actions;

export default reducer;