import {Dispatch} from "redux";
import search from "./search";
import {openSnackBar} from "../actions/SnackBarActions";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {newSearchResultsAction} from "../actions/SearchResultActions";
import * as H from "history";
import {queryExecutedAction} from "../actions/QueryActions";
import {documentLoadedAction} from "../actions/dialog/DocumentDialogAction";
import {SearchResult} from "../entities/SearchResult";
import {IndexedDocument} from "../entities/IndexedDocument";
import {loremIpsumText} from "./loremIpsum";

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

};

export const mockDocumentRequested = (searchResult: SearchResult, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const document: IndexedDocument = {
            body: loremIpsumText,
            title: searchResult.url,
            url: searchResult.url
        };
        dispatch(hideProgressBarAction());
        dispatch(documentLoadedAction(document));
    }, 2000);
};