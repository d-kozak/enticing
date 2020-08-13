import {createSlice, PayloadAction} from "redux-starter-kit";
import {
    addNewItemsToCollection,
    clearCollection,
    emptyPaginatedCollection,
    PaginatedCollection,
    PaginatedResult
} from "../entities/pagination";
import {ComponentInfo} from "../entities/ComponentInfo";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";
import {LogDto} from "../entities/LogDto";
import {generate} from "shortid";
import {snackbarOnError} from "../request/userRequests";

const {reducer, actions} = createSlice({
    slice: 'components',
    initialState: emptyPaginatedCollection<ComponentInfo>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<ComponentInfo>, actions: PayloadAction<PaginatedResult<ComponentInfo>>) => {
            addNewItemsToCollection(state, actions.payload, {
                stringifyId: true,
                nestedCollectionName: "logs",
                forEachElem: (elem) => {
                    elem.serverId = elem.serverId.toString();
                }
            })
        },
        addComponent: (state: PaginatedCollection<ComponentInfo>, action: PayloadAction<ComponentInfo>) => {
            const component = action.payload;
            const prev = state.elements[component.id];
            component.id = component.id.toString();
            component.logs = prev?.logs || emptyPaginatedCollection();
            state.elements[component.id] = component;
        },
        clearAll: (state: PaginatedCollection<ComponentInfo>) => {
            clearCollection(state);
        },
        addLogsToComponent: (state: PaginatedCollection<ComponentInfo>, action: PayloadAction<PaginatedResult<LogDto> & { componentId: string }>) => {
            const payload = action.payload;
            const component = state.elements[payload.componentId];
            if (!component) {
                console.error(`unknown component ${payload.componentId}`)
                return;
            }
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = generate()
                component.logs.index[offset + i] = elem.id;
                component.logs.elements[elem.id] = elem;
            }
            component.logs.totalElements = payload.totalElements
        },
        clearComponentLogs: (state: PaginatedCollection<ComponentInfo>, action: PayloadAction<string>) => {
            const id = action.payload;
            const component = state.elements[id];
            if (!component) {
                console.error(`unknown component ${id}`)
                return;
            }
            clearCollection(component.logs);
        },
        refresh: (state: PaginatedCollection<ComponentInfo>, action: PayloadAction<Array<ComponentInfo>>) => {
            clearCollection(state)
            for (let i = 0; i < action.payload.length; i++) {
                const component = action.payload[i];
                component.id = component.id.toString();
                state.elements[component.id] = component
                state.index[i] = component.id
            }
            state.totalElements = action.payload.length;
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