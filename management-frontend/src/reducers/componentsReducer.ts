import {createSlice, PayloadAction} from "redux-starter-kit";
import {PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {ComponentInfo} from "../entities/ComponentInfo";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";
import {LogDto} from "../entities/LogDto";
import {snackbarOnError} from "../request/userRequests";
import PaginatedCollections from "../entities/PaginatedCollections";

const {reducer, actions} = createSlice({
    slice: 'components',
    initialState: PaginatedCollections.emptyCollection<ComponentInfo>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<ComponentInfo>, actions: PayloadAction<PaginatedResult<ComponentInfo>>) => {
            PaginatedCollections.addAll(state, actions.payload, {
                stringifyId: true,
                nestedCollectionName: "logs",
                forEachElem: (elem) => {
                    elem.serverId = elem.serverId.toString();
                }
            })
        },
        addComponent: (state: PaginatedCollection<ComponentInfo>, action: PayloadAction<ComponentInfo>) => {
            PaginatedCollections.add(state, action.payload, {
                stringifyId: true,
                nestedCollectionName: "logs",
                forEachElem: (elem) => {
                    elem.serverId = elem.serverId.toString();
                }
            })
        },
        clearAll: (state: PaginatedCollection<ComponentInfo>) => {
            PaginatedCollections.clear(state);
        },
        addLogsToComponent: (state: PaginatedCollection<ComponentInfo>, action: PayloadAction<PaginatedResult<LogDto> & { componentId: string }>) => {
            const payload = action.payload;
            const component = state.elements[payload.componentId];
            if (!component) {
                console.error(`unknown component ${payload.componentId}`)
                return;
            }
            PaginatedCollections.addAll(component.logs, action.payload, {
                generateId: true
            })
        },
        clearComponentLogs: (state: PaginatedCollection<ComponentInfo>, action: PayloadAction<string>) => {
            const id = action.payload;
            const component = state.elements[id];
            if (!component) {
                console.error(`unknown component ${id}`)
                return;
            }
            PaginatedCollections.clear(component.logs);
        },
        refresh: (state: PaginatedCollection<ComponentInfo>, action: PayloadAction<Array<ComponentInfo>>) => {
            PaginatedCollections.refresh(state, action.payload, {
                stringifyId: true
            })
        },
    }
});

export const {addNewItems, addComponent, clearAll, addLogsToComponent, refresh, clearComponentLogs} = actions;

export const requestComponentInfo = (id: string): ThunkResult<void> => async (dispatch) => {
    try {
        const component = await getRequest<ComponentInfo>(`/component/${id}`);
        dispatch(addComponent(component));
    } catch (e) {
        console.error(e);
    }
}

export const requestAllComponents = (): ThunkResult<void> => async (dispatch) => {
    await snackbarOnError(dispatch, 'Failed to load components', async () => {
        let page = 0
        const pageSize = 100
        let res = await getRequest<PaginatedResult<ComponentInfo>>(`/component/`, [["page", page], ["size", pageSize]])
        const results = res.content
        while (res.totalElements > results.length) {
            res = await getRequest<PaginatedResult<ComponentInfo>>(`/component/`, [["page", ++page], ["size", pageSize]])
            results.push(...res.content)
        }
        dispatch(refresh(results))
    });
}

export default reducer;