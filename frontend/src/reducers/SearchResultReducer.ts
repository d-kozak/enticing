import {SEARCH_RESULT_UPDATED, SEARCH_RESULTS_NEW, SearchResultAction} from "../actions/SearchResultActions";
import {SearchResult} from "../entities/SearchResult";

const initialState = {
    searchResults: null as Array<SearchResult> | null
}

export type SearchResultsState = Readonly<typeof initialState>

type SearchResultReducer = (state: SearchResultsState | undefined, action: SearchResultAction) => SearchResultsState

const searchResultReducer: SearchResultReducer = (state = initialState, action) => {
    switch (action.type) {
        case SEARCH_RESULTS_NEW:
            return {
                searchResults: action.searchResults
            };
        case SEARCH_RESULT_UPDATED:
            if (!state.searchResults) {
                throw new Error("Invalid state, cannot update a single result when no results are in the state");
            }
            return {
                searchResults: state.searchResults
                    .map(item => item.id === action.searchResult.id ? action.searchResult : item)
            }
    }
    return state
}

export default searchResultReducer;