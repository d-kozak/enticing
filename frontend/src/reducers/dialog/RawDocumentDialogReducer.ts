import {createSlice, PayloadAction} from "redux-starter-kit";
import {RawDocumentDialogState} from "../../ApplicationState";
import {SearchResult} from "../../entities/SearchResult";
import axios from "axios";
import {API_BASE_PATH} from "../../globals";
import {consoleDump} from "../../components/utils/dump";
import {ThunkResult} from "../../actions/RootActions";
import {RawDocumentRequest} from "../../entities/RawDocumentRequest";
import {openSnackbar} from "../SnackBarReducer";

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


export const loadRawDocumentRequest = (searchResult: SearchResult): ThunkResult<void> => async dispatch => {
    const request: RawDocumentRequest = {
        server: searchResult.host,
        collection: searchResult.collection,
        documentId: searchResult.documentId
    };
    try {
        const response = await axios.post(`${API_BASE_PATH}/query/raw-document/`, request, {withCredentials: true});
        dispatch(openRawDocumentDialog({
            info: {
                host: searchResult.host,
                collection: searchResult.collection,
                documentId: searchResult.id,
                title: searchResult.documentTitle,
                url: searchResult.url,
                content: response.data
            }
        }));
    } catch (e) {
        dispatch(openSnackbar("Could not load raw document"));
        consoleDump(e);
    }
};