import {SearchResultsState} from "../ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {SearchResult} from "../entities/SearchResult";
import {CorpusFormat} from "../entities/CorpusFormat";


const {reducer, actions} = createSlice({
    slice: 'searchResults',
    initialState: {
        snippets: null as Array<SearchResult> | null,
        corpusFormat: null as CorpusFormat | null
    } as SearchResultsState,
    reducers: {
        newSearchResults: (state: SearchResultsState, {payload}: PayloadAction<SearchResultsState>) => {
            state.corpusFormat = payload.corpusFormat;
            state.snippets = payload.snippets;
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
        }
    }
});

export const {newSearchResults, updateSearchResult} = actions;
export default reducer;