import {FullDocument, isDocument} from "../../entities/FullDocument";
import {ThunkResult} from "../RootActions";
import {API_BASE_PATH} from "../../globals";
import axios from "axios";
import {Snippet} from "../../entities/Snippet";
import {DocumentQuery} from "../../entities/DocumentQuery";
import {parseNewAnnotatedText} from "../../components/annotations/NewAnnotatedText";
import {CorpusFormat} from "../../entities/CorpusFormat";
import {openSnackbar} from "../../reducers/SnackBarReducer";
import {hideProgressbar, showProgressbar} from "../../reducers/ProgressBarReducer";

export const DOCUMENT_DIALOG_DOCUMENT_LOADED = '[DOCUMENT DIALOG] DOCUMENT LOADED';
export const DOCUMENT_DIALOG_CLOSED = '[DOCUMENT DIALOG] CLOSED';

interface DocumentDialogLoadedAction {
    type: typeof DOCUMENT_DIALOG_DOCUMENT_LOADED
    document: FullDocument,
    corpusFormat: CorpusFormat
}

interface DialogClosedAction {
    type: typeof DOCUMENT_DIALOG_CLOSED
}


export type DocumentDialogAction = DocumentDialogLoadedAction | DialogClosedAction

export const documentLoadedAction = (document: FullDocument, corpusFormat: CorpusFormat): DocumentDialogLoadedAction => ({
    type: DOCUMENT_DIALOG_DOCUMENT_LOADED,
    document,
    corpusFormat
});

export const documentDialogClosedAction = (): DialogClosedAction => ({
    type: DOCUMENT_DIALOG_CLOSED
});

export const documentDialogRequestedAction = (searchResult: Snippet, corpusFormat: CorpusFormat): ThunkResult<void> => dispatch => {
    dispatch(showProgressbar())
    const documentQuery: DocumentQuery = {
        host: searchResult.host,
        collection: searchResult.collection,
        documentId: searchResult.documentId,
        defaultIndex: "token",
    };
    axios.post(`${API_BASE_PATH}/query/document`, documentQuery, {
        withCredentials: true
    }).then(response => {
        if (!isDocument(response.data)) {
            throw `Invalid document ${JSON.stringify(response.data, null, 2)}`;
        }
        const parsed = parseNewAnnotatedText(response.data.payload.content);
        if (parsed == null)
            throw "could not parse";
        response.data.payload.content = parsed
        dispatch(hideProgressbar());
        dispatch(documentLoadedAction(response.data, corpusFormat));
    }).catch(() => {
        dispatch(openSnackbar('Could not load document'));
        dispatch(hideProgressbar());
    })
};