import {ThunkResult} from "./RootAction";
import {mockSearch} from "../mocks/mockSearchApi";
import * as H from "history";

interface QueryExecutedAction {
    type: '[QUERY] QUERY EXECUTED',
    query: string
}

export type QueryAction = QueryExecutedAction

export const startSearchingAction = (query: string, history?: H.History): ThunkResult<void> => (dispatch) => {
    mockSearch(query, dispatch, history)
}

export const queryExecutedAction = (query: string): QueryExecutedAction => ({
    type: "[QUERY] QUERY EXECUTED",
    query
});