import {ProgressBarState} from "../AppState";
import {PROGRESS_BAR_HIDE, PROGRESS_BAR_SHOW, ProgressBarAction} from "../actions/ProgressBarActions";


type ProgressBarReducer = (state: ProgressBarState | undefined, action: ProgressBarAction) => ProgressBarState

const initialState: ProgressBarState = {
    isVisible: false
}

const progressBarReducer: ProgressBarReducer = (state = initialState, action) => {
    switch (action.type) {
        case PROGRESS_BAR_SHOW:
            return {
                isVisible: true
            };

        case PROGRESS_BAR_HIDE:
            return {
                isVisible: false
            }
    }

    return state;
}

export default progressBarReducer;