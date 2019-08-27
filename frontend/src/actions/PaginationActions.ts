import {ThunkResult} from "./RootActions";

export const setPageRequest = (currentResultSize: number, selectedSettings: number, wantedPage: number, resultsPerPage: number): ThunkResult<void> => dispatch => {
    const fullPages = Math.floor(currentResultSize / resultsPerPage);
    const extraOnLastPage = currentResultSize % resultsPerPage;
    const needMore = extraOnLastPage == 0 ? wantedPage == fullPages : wantedPage == fullPages + 1;


};