import {ThunkResult} from "./RootAction";
import {mockSearch} from "../mocks/searchApi";

export type QueryAction = { type: string }

export const startSearchingAction = (query: string): ThunkResult<void> => (dispatch) => {
    mockSearch(query, dispatch)
}