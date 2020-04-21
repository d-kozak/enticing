import {createSlice, PayloadAction} from "redux-starter-kit";
import {OperationsStatsState} from "../ApplicationState";


const {reducer, actions} = createSlice({
    slice: 'operationStats',
    initialState: {} as OperationsStatsState,
    reducers: {
        refreshStats: (state: OperationsStatsState, action: PayloadAction<OperationsStatsState>) => {
            for (const name of Object.getOwnPropertyNames(state)) delete state[name];
            for (const name of Object.getOwnPropertyNames(action.payload))
                state[name] = action.payload[name];
        },
    }
});

export const {refreshStats} = actions;

export default reducer;