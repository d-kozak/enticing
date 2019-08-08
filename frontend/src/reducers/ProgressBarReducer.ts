import {ProgressBarState} from "./ApplicationState";
import {createSlice} from "redux-starter-kit";


const {reducer, actions} = createSlice({
    slice: 'progressBar',
    initialState: {
        isVisible: false
    } as ProgressBarState,
    reducers: {
        showProgressbar: (state: ProgressBarState) => {
            state.isVisible = true;
        },
        hideProgressbar: (state: ProgressBarState) => {
            state.isVisible = false;
        }
    }
});

export const {showProgressbar, hideProgressbar} = actions;
export default reducer;
