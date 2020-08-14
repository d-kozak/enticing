import {createSlice, PayloadAction} from "redux-starter-kit";
import {PaginatedCollection, PaginatedCollections, PaginatedResult} from "../entities/pagination";
import {Corpus} from "../entities/Corpus";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";
import {ComponentInfo} from "../entities/ComponentInfo";

const {reducer, actions} = createSlice({
    slice: 'corpuses',
    initialState: PaginatedCollections.emptyCollection<Corpus>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<Corpus>, actions: PayloadAction<PaginatedResult<Corpus>>) => {
            PaginatedCollections.addAll(state, actions.payload, {
                stringifyId: true,
                nestedCollectionName: "components"
            })
        },
        addCorpus: (state: PaginatedCollection<Corpus>, action: PayloadAction<Corpus>) => {
            PaginatedCollections.add(state, action.payload, {
                stringifyId: true,
                nestedCollectionName: "components"
            })
        },
        clearAll: (state: PaginatedCollection<Corpus>) => {
            PaginatedCollections.clear(state);
        },
        addComponentsToCorpus: (state: PaginatedCollection<Corpus>, action: PayloadAction<PaginatedResult<ComponentInfo> & { corpusId: string }>) => {
            const payload = action.payload;
            const server = state.elements[payload.corpusId];
            if (!server) {
                console.error(`unknown corpus ${payload.corpusId}`)
                return;
            }
            PaginatedCollections.addAll(server.components, action.payload, {
                stringifyId: true
            });
        },
        clearComponentsFromCorpus: (state: PaginatedCollection<Corpus>, action: PayloadAction<string>) => {
            const id = action.payload;
            const corpus = state.elements[id];
            if (!corpus) {
                console.error(`unknown corpus ${id}`)
                return;
            }
            PaginatedCollections.clear(corpus.components);
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