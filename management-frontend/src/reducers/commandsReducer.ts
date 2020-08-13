import {createSlice, PayloadAction} from "redux-starter-kit";
import {
    addNewItemsToCollection,
    clearCollection,
    emptyPaginatedCollection,
    PaginatedCollection,
    PaginatedResult
} from "../entities/pagination";
import {CommandDto} from "../entities/CommandDto";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";

const {reducer, actions} = createSlice({
    slice: 'commands',
    initialState: emptyPaginatedCollection<CommandDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<CommandDto>, actions: PayloadAction<PaginatedResult<CommandDto>>) => {
            addNewItemsToCollection(state, actions.payload, {
                stringifyId: true
            })
        },
        addCommand: (state: PaginatedCollection<CommandDto>, action: PayloadAction<CommandDto>) => {
            const command = action.payload;
            command.id = command.id.toString();
            state.elements[command.id] = command;
        },
        clearAll: (state: PaginatedCollection<CommandDto>) => {
            clearCollection(state);
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