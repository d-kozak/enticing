import {SEARCH_RESULT_UPDATED, SEARCH_RESULTS_NEW, SearchResultAction} from "../actions/SearchResultActions";
import {initialState, SearchResultsState} from "./ApplicationState";


type SearchResultReducer = (state: SearchResultsState | undefined, action: SearchResultAction) => SearchResultsState

const searchResultReducer: SearchResultReducer = (state = initialState.searchResult, action) => {
    switch (action.type) {
        case SEARCH_RESULTS_NEW:
            return {
                snippets: action.snippets,
                corpusFormat: action.corpusFormat
            };
        case SEARCH_RESULT_UPDATED:
            if (!state.snippets) {
                throw new Error("Invalid state, cannot update a single result when no results are in the state");
            }
            return {
                ...state,
                snippets: state.snippets
                    .map(item => item.id === action.snippet.id ? action.snippet : item)
            }
    }
    return state
}

export default searchResultReducer;