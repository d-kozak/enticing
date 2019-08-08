import {DocumentDialogState} from "../ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {Snippet} from "../../entities/Snippet";
import {CorpusFormat} from "../../entities/CorpusFormat";
import {ThunkResult} from "../../actions/RootActions";
import {DocumentQuery} from "../../entities/DocumentQuery";
import {API_BASE_PATH} from "../../globals";
import {isDocument} from "../../entities/FullDocument";
import {parseNewAnnotatedText} from "../../components/annotations/NewAnnotatedText";
import {openSnackbar} from "../SnackBarReducer";
import {hideProgressbar, showProgressbar} from "../ProgressBarReducer";
import axios from "axios";


const {reducer, actions} = createSlice({
    slice: 'documentDialog',
    initialState: {
        document: null,
        corpusFormat: null
    } as DocumentDialogState,
    reducers: {
        openDocumentDialog: (state: DocumentDialogState, {payload}: PayloadAction<DocumentDialogState>) => {
            state.document = payload.document;
            state.corpusFormat = payload.corpusFormat;
        },
        closeDocumentDialog: (state: DocumentDialogState) => {
            state.document = null;
            state.corpusFormat = null;
        }
    }
});

export const {closeDocumentDialog,} = actions;
export default reducer;

export const openDocumentDialogRequest = (searchResult: Snippet, corpusFormat: CorpusFormat): ThunkResult<void> => dispatch => {
    dispatch(showProgressbar());
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
        response.data.payload.content = parsed;
        dispatch(hideProgressbar());
        dispatch(actions.openDocumentDialog({document: response.data, corpusFormat}));
    }).catch(() => {
        dispatch(openSnackbar('Could not load document'));
        dispatch(hideProgressbar());
    })
};