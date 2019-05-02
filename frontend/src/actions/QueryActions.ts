import {ThunkResult} from "./RootActions";
import {mockSearch} from "../mocks/mockSearchApi";
import * as H from "history";
import {SearchQuery} from "../entities/SearchQuery";

export const QUERY_EXECUTED = '[QUERY] QUERY EXECUTED';
export const QUERY_TOGGLE_USE_CONSTRAINTS = '[QUERY] TOGGLE USE CONSTRAINTS';

interface QueryExecutedAction {
    type: typeof QUERY_EXECUTED,
    query: SearchQuery
}

interface ToggleUseConstrainsAction {
    type: typeof QUERY_TOGGLE_USE_CONSTRAINTS
}

export type QueryAction = QueryExecutedAction | ToggleUseConstrainsAction

export const startSearchingAction = (query: SearchQuery, history?: H.History): ThunkResult<void> => (dispatch) => {
    mockSearch(query, dispatch, history)
}

export const queryExecutedAction = (query: SearchQuery): QueryExecutedAction => ({
    type: "[QUERY] QUERY EXECUTED",
    query
});

export const toggleUseConstrainsAction = (): ToggleUseConstrainsAction => ({
    type: "[QUERY] TOGGLE USE CONSTRAINTS"
});