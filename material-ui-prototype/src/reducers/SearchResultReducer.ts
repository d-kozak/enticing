import {SearchResultsState} from "../AppState";
import {SearchResultAction} from "../actions/SearchResultActions";


type SearchResultReducer = (state: SearchResultsState | undefined, action: SearchResultAction) => SearchResultsState

const initialState: SearchResultsState = {}

const searchResultReducer: SearchResultReducer = (state = initialState, action) => {
    return state
}

export default searchResultReducer;