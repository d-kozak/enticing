import {createSlice, PayloadAction} from "redux-starter-kit";
import {emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {ServerInfo} from "../entities/ServerInfo";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";


const {reducer, actions} = createSlice({
    slice: 'servers',
    initialState: emptyPaginatedCollection<ServerInfo>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<ServerInfo>, actions: PayloadAction<PaginatedResult<ServerInfo>>) => {
            const payload = actions.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = elem.id.toString(); // (in case it was parsed as a number, transform it back to string)
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            state.totalElements = payload.totalElements
        },
        addServer: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<ServerInfo>) => {
            const server = action.payload;
            server.id = server.id.toString();
            state.elements[server.id] = server;
        }
    }
});

export const {addNewItems, addServer} = actions;

export default reducer;

export const requestServerInfo = (id: string): ThunkResult<void> => async (dispatch) => {
    try {
        const server = await getRequest<ServerInfo>(`/server/${id}`);
        dispatch(addServer(server));
    } catch (e) {
        console.error(e);
    }
}