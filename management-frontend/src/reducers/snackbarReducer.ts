import {createSlice, PayloadAction} from "redux-starter-kit";
import {SnackbarState} from "../ApplicationState";


const {reducer, actions} = createSlice({
    slice: 'snackbar',
    initialState: {
        isOpen: false,
        message: ''
    } as SnackbarState,
    reducers: {
        openSnackbarAction: (state: SnackbarState, action: PayloadAction<string>) => {
            state.isOpen = true;
            state.message = action.payload;
        },
        closeSnackbarAction: (state: SnackbarState) => {
            state.isOpen = false;
            state.message = ''
        }
    }
});

export const {openSnackbarAction, closeSnackbarAction} = actions;

export default reducer;