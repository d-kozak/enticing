import {ThunkResult} from "./RootAction";
import {mockSearch} from "../mocks/searchApi";
import * as H from "history";

export type QueryAction = { type: string }

export const startSearchingAction = (query: string, history?: H.History): ThunkResult<void> => (dispatch) => {
    mockSearch(query, dispatch, history)
}