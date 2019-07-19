import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {documentLoadedAction} from "../actions/dialog/DocumentDialogAction";
import {Snippet} from "../entities/Snippet";
import {FullDocument} from "../entities/FullDocument";
import {loremIpsumLong} from "./loremIpsum";
import {mockCorpusFormat} from "./mockSearchApi";


export const mockDocumentRequested = (searchResult: Snippet, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const document: FullDocument = {
            host: "google.com",
            collection: "col1", documentId: 42,
            payload: {
                content: loremIpsumLong,
            },
            title: searchResult.url,
            url: searchResult.url,
            queryMapping: [],
        };
        dispatch(hideProgressBarAction());
        dispatch(documentLoadedAction(document, mockCorpusFormat));
    }, 2000);
};
