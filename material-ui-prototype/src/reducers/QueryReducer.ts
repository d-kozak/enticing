import {QueryState} from "../AppState";
import {QueryAction} from "../actions/QueryActions";


type QueryReducer = (state: QueryState | undefined, action: QueryAction) => QueryState

const initialState: QueryState = {
    lastQuery: 'nertag:person (visited|entered)'
}

const queryReducer: QueryReducer = (state = initialState, action) => {
    switch (action.type) {
        case "[QUERY] QUERY EXECUTED":
            return {
                lastQuery: action.query
            };
    }
    return state
}


export default queryReducer;
