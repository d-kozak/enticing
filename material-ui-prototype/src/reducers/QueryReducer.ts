import {QueryState} from "../AppState";
import {QueryAction} from "../actions/QueryActions";


type QueryReducer = (state: QueryState | undefined, action: QueryAction) => QueryState

const initialState: QueryState = {}

const queryReducer: QueryReducer = (state = initialState, action) => {
    return state
}


export default queryReducer;
