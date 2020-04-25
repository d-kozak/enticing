import {createSlice, PayloadAction} from "redux-starter-kit";
import {clearCollection, emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {ServerInfo} from "../entities/ServerInfo";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";
import {ComponentInfo} from "../entities/ComponentInfo";
import {snackbarOnError} from "../request/userRequests";

const {reducer, actions} = createSlice({
    slice: 'servers',
    initialState: emptyPaginatedCollection<ServerInfo>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<PaginatedResult<ServerInfo>>) => {
            const payload = action.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = elem.id.toString(); // (in case it was parsed as a number, transform it back to string)
                elem.components = emptyPaginatedCollection();
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            state.totalElements = payload.totalElements
        },
        addServer: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<ServerInfo>) => {
            const server = action.payload;
            server.id = server.id.toString();
            server.components = emptyPaginatedCollection();
            state.elements[server.id] = server;
        },
        addComponentsToServer: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<PaginatedResult<ComponentInfo> & { serverId: string }>) => {
            const payload = action.payload;
            const server = state.elements[payload.serverId];
            if (!server) {
                console.error(`unknown server ${payload.serverId}`)
                return;
            }
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = elem.id.toString(); // (in case it was parsed as a number, transform it back to string)
                server.components.index[offset + i] = elem.id;
                server.components.elements[elem.id] = elem;
            }
            server.components.totalElements = payload.totalElements
        },
        clearComponentsFromServer: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<string>) => {
            const id = action.payload;
            const server = state.elements[id];
            if (!server) {
                console.error(`unknown server ${id}`)
                return;
            }
            clearCollection(server.components);
        },
        refresh: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<Array<ServerInfo>>) => {
            clearCollection(state)
            for (let i = 0; i < action.payload.length; i++) {
                const server = action.payload[i];
                server.id = server.id.toString();
                state.elements[server.id] = server
                state.index[i] = server.id
            }
        },
        clearAll: (state: PaginatedCollection<ServerInfo>) => {
            clearCollection(state);
        }
    }
});

export const {addNewItems, addServer, addComponentsToServer, clearComponentsFromServer, refresh, clearAll} = actions;

export default reducer;

export const requestAllServers = (): ThunkResult<void> => async (dispatch) => {
    await snackbarOnError(dispatch, 'Failed to load servers', async () => {
        let page = 0
        const pageSize = 100
        let res = await getRequest<PaginatedResult<ServerInfo>>(`/server/`, [["page", page], ["size", pageSize]])
        const results = res.content
        while (res.totalElements > results.length) {
            res = await getRequest<PaginatedResult<ServerInfo>>(`/server/`, [["page", ++page], ["size", pageSize]])
            results.push(...res.content)
        }
        dispatch(refresh(results))
    });
}

export const requestServerInfo = (id: string): ThunkResult<void> => async (dispatch) => {
    try {
        const server = await getRequest<ServerInfo>(`/server/${id}`);
        dispatch(addServer(server));
    } catch (e) {
        console.error(e);
    }
}