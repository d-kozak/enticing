import {createSlice, PayloadAction} from "redux-starter-kit";
import {
    addNewItemsToCollection,
    clearCollection,
    emptyPaginatedCollection,
    PaginatedCollection,
    PaginatedResult
} from "../entities/pagination";
import {BugReport} from "../entities/BugReport";


const {reducer, actions} = createSlice({
    slice: 'bugReports',
    initialState: emptyPaginatedCollection<BugReport>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<BugReport>, actions: PayloadAction<PaginatedResult<BugReport>>) => {
            addNewItemsToCollection(state, actions.payload, {
                stringifyId: true
            })
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