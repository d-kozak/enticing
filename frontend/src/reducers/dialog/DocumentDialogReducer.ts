import {
    DOCUMENT_DIALOG_CLOSED,
    DOCUMENT_DIALOG_DOCUMENT_LOADED,
    DocumentDialogAction
} from "../../actions/dialog/DocumentDialogAction";
import {FullDocument} from "../../entities/FullDocument";
import {CorpusFormat} from "../../entities/CorpusFormat";

const initialState = {
    document: null as FullDocument | null,
    corpusFormat: null as CorpusFormat | null
}

export type DocumentDialogState = Readonly<typeof initialState>

type DocumentDialogReducer = (state: DocumentDialogState | undefined, action: DocumentDialogAction) => DocumentDialogState

const documentDialogReducer: DocumentDialogReducer = (state = initialState, action) => {
    switch (action.type) {
        case DOCUMENT_DIALOG_DOCUMENT_LOADED:
            return {
                document: action.document,
                corpusFormat: action.corpusFormat
            };

        case DOCUMENT_DIALOG_CLOSED:
            return {
                document: null,
                corpusFormat: null
            }
    }
    return state;
};

export default documentDialogReducer;