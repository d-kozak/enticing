import {createSlice, PayloadAction} from "redux-starter-kit";
import {emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {ComponentInfo} from "../entities/ComponentInfo";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";
import {LogDto} from "../entities/LogDto";
import {generate} from "shortid";

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
                elem.logs = emptyPaginatedCollection();
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            state.totalElements = payload.totalElements;
        },
        addComponent: (state: PaginatedCollection<ComponentInfo>, action: PayloadAction<ComponentInfo>) => {
            const component = action.payload;
            component.id = component.id.toString();
            component.logs = emptyPaginatedCollection();
            state.elements[component.id] = component;
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
        }
    }
});

export const {addNewItems, addComponent, addLogsToComponent} = actions;

export const requestComponentInfo = (id: string): ThunkResult<void> => async (dispatch) => {
    try {
        const component = await getRequest<ComponentInfo>(`/component/${id}`);
        dispatch(addComponent(component));
    } catch (e) {
        console.error(e);
    }
}

export default reducer;