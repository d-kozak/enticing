import {createSlice, PayloadAction} from "redux-starter-kit";
import {
    addNewItemsToCollection,
    clearCollection,
    emptyPaginatedCollection,
    PaginatedCollection,
    PaginatedResult
} from "../entities/pagination";
import {Corpus} from "../entities/Corpus";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";
import {ComponentInfo} from "../entities/ComponentInfo";

const {reducer, actions} = createSlice({
    slice: 'corpuses',
    initialState: emptyPaginatedCollection<Corpus>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<Corpus>, actions: PayloadAction<PaginatedResult<Corpus>>) => {
            addNewItemsToCollection(state, actions.payload, {
                stringifyId: true,
                nestedCollectionName: "components"
            })
        },
        addCorpus: (state: PaginatedCollection<Corpus>, action: PayloadAction<Corpus>) => {
            const corpus = action.payload;
            corpus.id = corpus.id.toString();
            const prev = state.elements[corpus.id];
            corpus.components = prev?.components || emptyPaginatedCollection();
            state.elements[corpus.id] = corpus;
        },
        clearAll: (state: PaginatedCollection<Corpus>) => {
            clearCollection(state);
        },
        addComponentsToCorpus: (state: PaginatedCollection<Corpus>, action: PayloadAction<PaginatedResult<ComponentInfo> & { corpusId: string }>) => {
            const payload = action.payload;
            const server = state.elements[payload.corpusId];
            if (!server) {
                console.error(`unknown corpus ${payload.corpusId}`)
                return;
            }
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = elem.id.toString(); // (in case it was parsed as a number, transform it back to string)
                server.components.index[offset + i] = elem.id;
                server.components.elements[elem.id] = elem;
            }

            for (let i = payload.content.length; i < payload.size; i++) {
                delete server.components.index[offset + i]
            }

            server.components.totalElements = payload.totalElements
        },
        clearComponentsFromCorpus: (state: PaginatedCollection<Corpus>, action: PayloadAction<string>) => {
            const id = action.payload;
            const corpus = state.elements[id];
            if (!corpus) {
                console.error(`unknown corpus ${id}`)
                return;
            }
            clearCollection(corpus.components);
        }
    }
});

export const {addNewItems, addComponentsToCorpus, clearComponentsFromCorpus, addCorpus, clearAll} = actions;

export default reducer;

export const requestCorpus = (id: string): ThunkResult<void> => async (dispatch) => {
    try {
        const corpus = await getRequest<Corpus>(`/corpus/${id}`);
        dispatch(addCorpus(corpus));
    } catch (e) {
        console.error(e);
    }
}