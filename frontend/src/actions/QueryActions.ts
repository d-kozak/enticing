import {ThunkResult} from "./RootActions";
import {mockSearch} from "../mocks/mockSearchApi";
import * as H from "history";
import {SearchQuery} from "../entities/SearchQuery";

export const startSearchingAction = (query: SearchQuery, history?: H.History): ThunkResult<void> => (dispatch) => {
    mockSearch(query, dispatch, history)
}

