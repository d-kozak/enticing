import {createSlice, PayloadAction} from "redux-starter-kit";
import {PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {CommandDto} from "../entities/CommandDto";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";
import PaginatedCollections from "../entities/PaginatedCollections";

const {reducer, actions} = createSlice({
    slice: 'commands',
    initialState: PaginatedCollections.emptyCollection<CommandDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<CommandDto>, actions: PayloadAction<PaginatedResult<CommandDto>>) => {
            PaginatedCollections.addAll(state, actions.payload, {
                stringifyId: true
            })
        },
        addCommand: (state: PaginatedCollection<CommandDto>, action: PayloadAction<CommandDto>) => {
            PaginatedCollections.add(state, action.payload, {
                stringifyId: true
            })
        },
        clearAll: (state: PaginatedCollection<CommandDto>) => {
            PaginatedCollections.clear(state);
        }
    }
});

export const {addNewItems, addCommand, clearAll} = actions;

export default reducer;

export const requestCommandInfo = (id: string): ThunkResult<void> => async (dispatch) => {
    try {
        const command = await getRequest<CommandDto>(`/command/${id}`);
        dispatch(addCommand(command));
    } catch (e) {
        console.error(e);
    }
}