import {SearchResult} from "../entities/SearchResult";
import {ThunkResult} from "./RootActions";
import {mockContextRequested} from "../mocks/mockContextApi";

export const contextExtensionRequestAction = (searchResult: SearchResult): ThunkResult<void> => dispatch => {
    mockContextRequested(searchResult, dispatch)
};