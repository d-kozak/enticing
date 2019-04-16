import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {documentLoadedAction} from "../actions/dialog/DocumentDialogAction";
import {SearchResult} from "../entities/SearchResult";
import {IndexedDocument} from "../entities/IndexedDocument";
import {loremIpsumLong} from "./loremIpsum";


export const mockDocumentRequested = (searchResult: SearchResult, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const document: IndexedDocument = {
            body: {
                text: loremIpsumLong,
                annotations: new Map(),
                positions: []
            },
            title: searchResult.url,
            url: searchResult.url
        };
        dispatch(hideProgressBarAction());
        dispatch(documentLoadedAction(document));
    }, 2000);
};
