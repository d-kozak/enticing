import {ThunkResult} from "./RootAction";
import {mockSearch} from "../mocks/mockSearchApi";
import * as H from "history";
import {SearchQuery} from "../entities/SearchQuery";

interface QueryExecutedAction {
    type: '[QUERY] QUERY EXECUTED',
    query: SearchQuery
}

interface ToggleUseConstrainsAction {
    type: '[QUERY] TOGGLE USE CONSTRAINTS'
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