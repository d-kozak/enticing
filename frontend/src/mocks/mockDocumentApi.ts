import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {documentLoadedAction} from "../actions/dialog/DocumentDialogAction";
import {SearchResult} from "../entities/SearchResult";
import {IndexedDocument} from "../entities/IndexedDocument";
import {loremIpsumLong} from "./loremIpsum";
import {EdSheeran} from "./mockAnnotations";


export const mockDocumentRequested = (searchResult: SearchResult, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const document: IndexedDocument = {
            body: {
                text: loremIpsumLong,
                annotations: new Map([[1, EdSheeran]]),
                positions: [{annotationId: 1, from: 99, to: 109}],
                queryMapping: []
            },
            title: searchResult.url,
            url: searchResult.url
        };
        dispatch(hideProgressBarAction());
        dispatch(documentLoadedAction(document));
    }, 2000);
};
