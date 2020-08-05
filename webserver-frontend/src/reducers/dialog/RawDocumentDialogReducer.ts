import {createSlice, PayloadAction} from "redux-starter-kit";
import {RawDocumentDialogState} from "../../ApplicationState";
import {SearchResult} from "../../entities/SearchResult";
import axios from "axios";
import {API_BASE_PATH} from "../../globals";
import {consoleDump} from "../../components/utils/dump";
import {ThunkResult} from "../../actions/RootActions";
import {RawDocumentRequest} from "../../entities/RawDocumentRequest";
import {openSnackbar} from "../SnackBarReducer";
import {downloadFile} from "../../utils/file";

const {reducer, actions} = createSlice({
    slice: 'rawDocumentDialog',
    initialState: {
        info: null
    } as RawDocumentDialogState,
    reducers: {
        openRawDocumentDialog: (state: RawDocumentDialogState, {payload}: PayloadAction<RawDocumentDialogState>) => {
            state.info = payload.info;
        },
        closeRawDocumentDialog: (state: RawDocumentDialogState) => {
            state.info = null;
        }
    }
});

export const {openRawDocumentDialog, closeRawDocumentDialog} = actions;
export default reducer;


const getDocumentContent = async (searchResult: SearchResult) => {
    const request: RawDocumentRequest = {
        server: searchResult.host,
        collection: searchResult.collection,
        documentId: searchResult.documentId
    };
    return axios.post<string>(`${API_BASE_PATH}/query/raw-document/`, request, {withCredentials: true});
}

export const downloadRawDocumentRequest = (searchResult: SearchResult): ThunkResult<void> => async dispatch => {
    try {
        const response = await getDocumentContent(searchResult);
        downloadFile(searchResult.documentTitle + ".mg4j", response.data);
    } catch (e) {
        dispatch(openSnackbar("Could not download raw document"));
        consoleDump(e);
    }
};

export const loadRawDocumentRequest = (searchResult: SearchResult): ThunkResult<void> => async dispatch => {
    try {
        const response = await getDocumentContent(searchResult);
        dispatch(openRawDocumentDialog({
            info: {
                host: searchResult.host,
                collection: searchResult.collection,
                documentId: searchResult.id,
                title: searchResult.documentTitle,
                url: searchResult.url,
                content: response.data,
                location: searchResult.payload.location,
                size: searchResult.payload.size
            }
        }));
    } catch (e) {
        dispatch(openSnackbar("Could not load raw document"));
        consoleDump(e);
    }
};