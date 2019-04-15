import {Dispatch} from "redux";
import search from "./search";
import {openSnackBar} from "../actions/SnackBarActions";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {newSearchResultsAction} from "../actions/SearchResultActions";

export const mockSearch = (query: string, dispatch: Dispatch) => {

    dispatch(showProgressBarAction())
    search(query)
        .then(results => {
            dispatch(newSearchResultsAction(results));
        }).catch(error => {
        dispatch(openSnackBar(`Error ${error}`));
    }).finally(() => {
        dispatch(hideProgressBarAction());
    });

}