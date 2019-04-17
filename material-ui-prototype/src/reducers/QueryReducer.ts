import {QueryState} from "../AppState";
import {QueryAction} from "../actions/QueryActions";


type QueryReducer = (state: QueryState | undefined, action: QueryAction) => QueryState

const initialState: QueryState = {
    useConstraints: false,
    lastQuery: {
        query: 'nertag:person (visited|entered)',
        constraints: ''
    }
}

const queryReducer: QueryReducer = (state = initialState, action) => {
    switch (action.type) {
        case "[QUERY] QUERY EXECUTED":
            return {
                ...state,
                lastQuery: action.query
            };
        case "[QUERY] TOGGLE USE CONSTRAINTS":
            return {
                ...state,
                useConstraints: !state.useConstraints
            };
    }
    return state
}


export default queryReducer;
