import {DocumentDialogState} from "../../ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {CorpusFormat} from "../../entities/CorpusFormat";
import {ThunkResult} from "../../actions/RootActions";
import {DocumentQuery} from "../../entities/DocumentQuery";
import {API_BASE_PATH} from "../../globals";
import {isDocument} from "../../entities/FullDocument";
import {emptyTextUnitList, parseNewAnnotatedText} from "../../components/annotations/TextUnitList";
import {openSnackbar} from "../SnackBarReducer";
import {hideProgressbar, showProgressbar} from "../ProgressBarReducer";
import axios from "axios";
import {createFullMetadataRequest} from "../../actions/metadataFiltering";
import {PerfTimer} from "../../utils/perf";

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

export const openDocumentDialogRequest = (searchResultId: string, corpusFormat: CorpusFormat): ThunkResult<void> => (dispatch, getState) => {
    const searchResult = getState().searchResult.snippetsById[searchResultId];
    if (!searchResult) {
        console.error(`could not find search result with id ${searchResultId}`);
        return;
    }
    dispatch(showProgressbar());
    const documentQuery: DocumentQuery = {
        host: searchResult.host,
        collection: searchResult.collection,
        documentId: searchResult.documentId,
        metadata: createFullMetadataRequest(corpusFormat),
        defaultIndex: "token",
        textFormat: "TEXT_UNIT_LIST"
    };
    const timer = new PerfTimer('DocumentRequest');
    timer.sample('before request');
    const startTime = performance.now();
    axios.post(`${API_BASE_PATH}/query/document`, documentQuery, {
        withCredentials: true
    }).then(response => {
        timer.sample('received response');
        if (!isDocument(response.data)) {
            throw `Invalid document ${JSON.stringify(response.data, null, 2)}`;
        }
        const parsed = parseNewAnnotatedText(response.data.payload.content);
        if (parsed == null)
            throw "could not parse";
        response.data.payload.content = emptyTextUnitList;
        response.data.payload.parsedContent = parsed;
        timer.sample('dispatching result');
        dispatch(hideProgressbar());
        dispatch(actions.openDocumentDialog({
            document: response.data,
            corpusFormat
        }));
        timer.finish();
    }).catch(() => {
        dispatch(openSnackbar('Could not load document'));
        dispatch(hideProgressbar());
    })
};