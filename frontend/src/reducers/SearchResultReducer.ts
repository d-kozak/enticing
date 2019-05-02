import {SEARCH_RESULTS_NEW, SearchResultAction} from "../actions/SearchResultActions";
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
    }
    return state
}

export default searchResultReducer;