import {createSlice, PayloadAction} from "redux-starter-kit";
import {emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {ServerInfo} from "../entities/ServerInfo";

const {reducer, actions} = createSlice({
    slice: 'servers',
    initialState: emptyPaginatedCollection<ServerInfo>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<ServerInfo>, actions: PayloadAction<PaginatedResult<ServerInfo>>) => {
            const payload = actions.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++)
                state.content[offset + i] = payload.content[i];
            state.totalElements = payload.totalElements
        }
    }
});

export const {addNewItems} = actions;

export default reducer;