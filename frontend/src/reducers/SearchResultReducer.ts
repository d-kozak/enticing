import {SEARCH_RESULTS_NEW, SearchResultAction} from "../actions/SearchResultActions";
import {SearchResult} from "../entities/SearchResult";

/**
 * Interface is kept instead of type inference, because 'typing' nulls is ugly
 */
export interface SearchResultsState {
    readonly searchResults: Array<SearchResult> | null
}

const initialState: SearchResultsState = {
    searchResults: null
}

type SearchResultReducer = (state: SearchResultsState | undefined, action: SearchResultAction) => SearchResultsState

const searchResultReducer: SearchResultReducer = (state = initialState, action) => {
    switch (action.type) {
        case SEARCH_RESULTS_NEW:
            return {
                searchResults: action.searchResults
            };
    }
    return state
}

export default searchResultReducer;