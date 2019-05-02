import {SearchResultsState} from "../AppState";
import {SEARCH_RESULTS_NEW, SearchResultAction} from "../actions/SearchResultActions";


type SearchResultReducer = (state: SearchResultsState | undefined, action: SearchResultAction) => SearchResultsState

const initialState: SearchResultsState = {
    searchResults: null
}

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