import {QUERY_EXECUTED, QUERY_TOGGLE_USE_CONSTRAINTS, QueryAction} from "../actions/QueryActions";

const initialState = {
    useConstraints: false,
    lastQuery: {
        query: 'nertag:person (visited|entered)',
        constraints: ''
    }
}

export type QueryState = Readonly<typeof initialState>

type QueryReducer = (state: QueryState | undefined, action: QueryAction) => QueryState

const queryReducer: QueryReducer = (state = initialState, action) => {
    switch (action.type) {
        case QUERY_EXECUTED:
            return {
                ...state,
                lastQuery: action.query
            };
        case QUERY_TOGGLE_USE_CONSTRAINTS:
            return {
                ...state,
                useConstraints: !state.useConstraints
            };
    }
    return state
}


export default queryReducer;
