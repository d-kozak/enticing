import {createSlice, PayloadAction} from "redux-starter-kit";
import {PaginatedCollection, PaginatedResult,} from "../entities/pagination";
import {ServerInfo} from "../entities/ServerInfo";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";
import {ComponentInfo} from "../entities/ComponentInfo";
import {snackbarOnError} from "../request/userRequests";
import PaginatedCollections from "../entities/PaginatedCollections";


const {reducer, actions} = createSlice({
    slice: 'servers',
    initialState: PaginatedCollections.emptyCollection<ServerInfo>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<PaginatedResult<ServerInfo>>) => {
            PaginatedCollections.addAll(state, action.payload, {
                stringifyId: true,
                nestedCollectionName: 'components'
            })
        },
        addServer: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<ServerInfo>) => {
            PaginatedCollections.add(state, action.payload, {
                stringifyId: true,
                nestedCollectionName: 'components'
            })
        },
        addComponentsToServer: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<PaginatedResult<ComponentInfo> & { serverId: string }>) => {
            const payload = action.payload;
            const server = state.elements[payload.serverId];
            if (!server) {
                console.error(`unknown server ${payload.serverId}`)
                return;
            }
            PaginatedCollections.addAll(server.components, action.payload, {
                stringifyId: true
            })
        },
        clearComponentsFromServer: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<string>) => {
            const id = action.payload;
            const server = state.elements[id];
            if (!server) {
                console.error(`unknown server ${id}`)
                return;
            }
            PaginatedCollections.clear(server.components);
        },
        refresh: (state: PaginatedCollection<ServerInfo>, action: PayloadAction<Array<ServerInfo>>) => {
            PaginatedCollections.refresh(state, action.payload, {
                stringifyId: true,
                nestedCollectionName: 'components'
            });
        },
        clearAll: (state: PaginatedCollection<ServerInfo>) => {
            PaginatedCollections.clear(state);
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