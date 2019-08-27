import {SearchResultsState} from "../ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {SearchResult} from "../entities/SearchResult";


const {reducer, actions} = createSlice({
    slice: 'searchResults',
    initialState: {
        snippets: null,
        corpusFormat: null,
        moreResultsAvailable: false
    } as SearchResultsState,
    reducers: {
        newSearchResults: (state: SearchResultsState, {payload}: PayloadAction<SearchResultsState>) => {
            state.corpusFormat = payload.corpusFormat;
            state.snippets = payload.snippets;
            state.moreResultsAvailable = payload.moreResultsAvailable;
        },
        appendMoreSearchResults: (state: SearchResultsState, {payload}: PayloadAction<{ searchResults: Array<SearchResult>, hasMore: boolean }>) => {
            if (state.snippets == null) throw Error("Cannot append results, original array is null");
            state.snippets.push(...payload.searchResults);
            state.moreResultsAvailable = payload.hasMore;
        },
        updateSearchResult: (state: SearchResultsState, {payload}: PayloadAction<SearchResult>) => {
            if (!state.snippets) {
                throw new Error("Invalid state, cannot update a single result when no results are in the state");
            }
            const index = state.snippets.findIndex(snippet => snippet.id == payload.id);
            if (index !== -1) {
                state.snippets[index] = payload;
            } else {
                throw new Error("could not find corresponding snippet");
            }
        },
        setMoreResultsAvailable: (state: SearchResultsState, {payload}: PayloadAction<boolean>) => {
            state.moreResultsAvailable = payload;
        }
    }
});

export const {newSearchResults, updateSearchResult, setMoreResultsAvailable, appendMoreSearchResults} = actions;
export default reducer;