import {createSlice, PayloadAction} from "redux-starter-kit";
import {clearCollection, emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {Corpus} from "../entities/Corpus";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";

const {reducer, actions} = createSlice({
    slice: 'corpuses',
    initialState: emptyPaginatedCollection<Corpus>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<Corpus>, actions: PayloadAction<PaginatedResult<Corpus>>) => {
            const payload = actions.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = elem.id.toString(); // (in case it was parsed as a number, transform it back to string)
                elem.components = elem.components.map(c => c.toString()) // map numbers back to strings
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            state.totalElements = payload.totalElements;
        },
        addCorpus: (state: PaginatedCollection<Corpus>, action: PayloadAction<Corpus>) => {
            const corpus = action.payload;
            corpus.id = corpus.id.toString();
            corpus.components = corpus.components.map(c => c.toString()) // map numbers back to strings
            state.elements[corpus.id] = corpus;
        },
        clearAll: (state: PaginatedCollection<Corpus>) => {
            clearCollection(state);
        }
    }
});

export const {addNewItems, addCorpus, clearAll} = actions;

export default reducer;

export const requestCorpus = (id: string): ThunkResult<void> => async (dispatch) => {
    try {
        const corpus = await getRequest<Corpus>(`/corpus/${id}`);
        dispatch(addCorpus(corpus));
    } catch (e) {
        console.error(e);
    }
}