import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {documentLoadedAction} from "../actions/dialog/DocumentDialogAction";
import {Match} from "../entities/Snippet";
import {IndexedDocument} from "../entities/IndexedDocument";
import {loremIpsumLong} from "./loremIpsum";
import {EdSheeran} from "./mockAnnotations";
import {MatchedRegion} from "../entities/Annotation";


export const mockDocumentRequested = (searchResult: Match, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const document: IndexedDocument = {
            body: {
                text: loremIpsumLong,
                annotations: {
                    "ed": EdSheeran
                },
                positions: [{
                    annotationId: "ed", match: new MatchedRegion(99, 10), subAnnotations: []
                }],
                queryMapping: []
            },
            title: searchResult.url,
            url: searchResult.url
        };
        dispatch(hideProgressBarAction());
        dispatch(documentLoadedAction(document));
    }, 2000);
};
