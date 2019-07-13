import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {documentLoadedAction} from "../actions/dialog/DocumentDialogAction";
import {Snippet} from "../entities/Snippet";
import {FullDocument} from "../entities/FullDocument";
import {loremIpsumLong} from "./loremIpsum";
import {EdSheeran} from "./mockAnnotations";
import {MatchedRegion} from "../entities/Annotation";


export const mockDocumentRequested = (searchResult: Snippet, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const document: FullDocument = {
            host: "google.com",
            collection: "col1", documentId: 42,
            payload: {
                content: {
                    text: loremIpsumLong,
                    annotations: {
                        "ed": EdSheeran
                    },
                    positions: [{
                        annotationId: "ed", match: new MatchedRegion(99, 10), subAnnotations: []
                    }],
                    queryMapping: []
                }
            },
            title: searchResult.url,
            url: searchResult.url,
            queryMapping: [],
        };
        dispatch(hideProgressBarAction());
        dispatch(documentLoadedAction(document));
    }, 2000);
};
