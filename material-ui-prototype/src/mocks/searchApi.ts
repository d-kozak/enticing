import {Dispatch} from "redux";
import search from "./search";
import {openSnackBar} from "../actions/SnackBarActions";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {newSearchResultsAction} from "../actions/SearchResultActions";
import * as H from "history";
import {queryExecutedAction} from "../actions/QueryActions";

export const mockSearch = (query: string, dispatch: Dispatch, history?: H.History) => {

    dispatch(showProgressBarAction())
    dispatch(queryExecutedAction(query));
    search(query)
        .then(results => {
            dispatch(newSearchResultsAction(results));
            if (history) {
                history.push('/search');
            }
        }).catch(error => {
        dispatch(openSnackBar(`Error ${error}`));
    }).finally(() => {
        dispatch(hideProgressBarAction());
    });

}