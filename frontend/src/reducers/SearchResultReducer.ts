import {SEARCH_RESULT_UPDATED, SEARCH_RESULTS_NEW, SearchResultAction} from "../actions/SearchResultActions";
import {Match} from "../entities/Snippet";

const initialState = {
    snippets: null as Array<Match> | null
}

export type SearchResultsState = Readonly<typeof initialState>

type SearchResultReducer = (state: SearchResultsState | undefined, action: SearchResultAction) => SearchResultsState

const searchResultReducer: SearchResultReducer = (state = initialState, action) => {
    switch (action.type) {
        case SEARCH_RESULTS_NEW:
            return {
                snippets: action.snippets
            };
        case SEARCH_RESULT_UPDATED:
            if (!state.snippets) {
                throw new Error("Invalid state, cannot update a single result when no results are in the state");
            }
            return {
                snippets: state.snippets
                    .map(item => item.id === action.snippet.id ? action.snippet : item)
            }
    }
    return state
}

export default searchResultReducer;