import {SEARCH_RESULT_UPDATED, SEARCH_RESULTS_NEW, SearchResultAction} from "../actions/SearchResultActions";
import {Snippet} from "../entities/Snippet";
import {CorpusFormat} from "../entities/CorpusFormat";

const initialState = {
    snippets: null as Array<Snippet> | null,
    corpusFormat: null as CorpusFormat | null
}

export type SearchResultsState = Readonly<typeof initialState>

type SearchResultReducer = (state: SearchResultsState | undefined, action: SearchResultAction) => SearchResultsState

const searchResultReducer: SearchResultReducer = (state = initialState, action) => {
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