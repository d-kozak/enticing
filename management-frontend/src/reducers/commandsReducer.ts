import {createSlice, PayloadAction} from "redux-starter-kit";
import {emptyPaginatedCollection, PaginatedCollection, PaginatedResult} from "../entities/pagination";
import {CommandDto} from "../entities/CommandDto";
import {ThunkResult} from "../utils/ThunkResult";
import {getRequest} from "../network/requests";

const {reducer, actions} = createSlice({
    slice: 'commands',
    initialState: emptyPaginatedCollection<CommandDto>(),
    reducers: {
        addNewItems: (state: PaginatedCollection<CommandDto>, actions: PayloadAction<PaginatedResult<CommandDto>>) => {
            const payload = actions.payload;
            const offset = payload.number * payload.size;
            for (let i = 0; i < payload.content.length; i++) {
                const elem = payload.content[i];
                elem.id = elem.id.toString(); // (in case it was parsed as a number, transform it back to string)
                state.index[offset + i] = elem.id;
                state.elements[elem.id] = elem;
            }
            state.totalElements = payload.totalElements;
        },
        addCommand: (state: PaginatedCollection<CommandDto>, action: PayloadAction<CommandDto>) => {
            const command = action.payload;
            command.id = command.id.toString();
            state.elements[command.id] = command;
        },
    }
});

export const {addNewItems, addCommand} = actions;

export default reducer;

export const requestCommandInfo = (id: string): ThunkResult<void> => async (dispatch) => {
    try {
        const command = await getRequest<CommandDto>(`/command/${id}`);
        dispatch(addCommand(command));
    } catch (e) {
        console.error(e);
    }
}