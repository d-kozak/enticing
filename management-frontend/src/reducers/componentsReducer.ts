import {createSlice, PayloadAction} from "redux-starter-kit";
import {emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {ComponentInfo} from "../entities/ComponentInfo";

const {reducer, actions} = createSlice({
    slice: 'components',
    initialState: emptyPaginatedCollection<ComponentInfo>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<ComponentInfo>, actions: PayloadAction<PaginatedResult<ComponentInfo>>) => {
            const payload = actions.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = elem.id.toString(); // (in case it was parsed as a number, transform it back to string)
                elem.serverId = elem.serverId.toString();
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            state.totalElements = payload.totalElements;
        }
    }
});

export const {addNewItems} = actions;

export default reducer;