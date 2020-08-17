import {createSlice, PayloadAction} from "redux-starter-kit";
import {PaginatedCollection, PaginatedResult,} from "../entities/pagination";
import {BugReport} from "../entities/BugReport";
import PaginatedCollections from "../entities/PaginatedCollections";

const {reducer, actions} = createSlice({
    slice: 'bugReports',
    initialState: PaginatedCollections.emptyCollection<BugReport>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<BugReport>, actions: PayloadAction<PaginatedResult<BugReport>>) => {
            PaginatedCollections.addAll(state, actions.payload, {
                stringifyId: true
            })
        },
        removeReport: (state: PaginatedCollection<BugReport>, action: PayloadAction<BugReport>) => {
            PaginatedCollections.remove(state, action.payload);
        },
        clearAll: (state: PaginatedCollection<BugReport>) => {
            PaginatedCollections.clear(state);
        }
    }
});

export const {addNewItems, removeReport, clearAll} = actions;

export default reducer;