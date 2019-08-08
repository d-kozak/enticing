import {SnackbarState} from "./ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";

const snackbarSlice = createSlice({
    slice: 'snackbar',
    initialState: {
        isOpen: false,
        message: ''
    } as SnackbarState,
    reducers: {
        openSnackbar: (state: SnackbarState, action: PayloadAction<string>) => {
            state.isOpen = true;
            state.message = action.payload;
        },
        closeSnackbar: (state: SnackbarState) => {
            state.isOpen = false;
            state.message = '';
        }
    }
});

export const snackbarActions = snackbarSlice.actions;

export default snackbarSlice.reducer;
