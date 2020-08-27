import {createSlice, PayloadAction} from "redux-starter-kit";
import {PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {CommandDto} from "../entities/CommandDto";
import PaginatedCollections from "../entities/PaginatedCollections";
import {ThunkResult} from "../utils/ThunkResult";
import {snackbarOnError} from "../request/userRequests";
import {getRequest} from "../network/requests";

const {reducer, actions} = createSlice({
    slice: 'builds',
    initialState: PaginatedCollections.emptyCollection<CommandDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<CommandDto>, actions: PayloadAction<PaginatedResult<CommandDto>>) => {
            PaginatedCollections.addAll(state, actions.payload, {
                stringifyId: true
            })
        },
        refresh: (state: PaginatedCollection<CommandDto>, action: PayloadAction<Array<CommandDto>>) => {
            PaginatedCollections.refresh(state, action.payload, {
                stringifyId: true
            });
        },
        clearAll: (state: PaginatedCollection<CommandDto>) => {
            PaginatedCollections.clear(state);
        }
    }
});

export const {addNewItems, refresh, clearAll} = actions;

export const requestAllBuilds = (): ThunkResult<void> => async (dispatch) => {
    await snackbarOnError(dispatch, 'Failed to load builds', async () => {
        let page = 0
        const pageSize = 100
        let res = await getRequest<PaginatedResult<CommandDto>>(`/build/`, [["page", page], ["size", pageSize]])
        const results = res.content
        while (res.totalElements > results.length) {
            res = await getRequest<PaginatedResult<CommandDto>>(`/build/`, [["page", ++page], ["size", pageSize]])
            results.push(...res.content)
        }
        dispatch(refresh(results))
    });
}

export default reducer;