import {PROGRESS_BAR_HIDE, PROGRESS_BAR_SHOW, ProgressBarAction} from "../actions/ProgressBarActions";
import {initialState, ProgressBarState} from "./ApplicationState";


type ProgressBarReducer = (state: ProgressBarState | undefined, action: ProgressBarAction) => ProgressBarState

const progressBarReducer: ProgressBarReducer = (state = initialState.progressBar, action) => {
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