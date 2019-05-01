import {ProgressBarState} from "../AppState";
import {ProgressBarAction} from "../actions/ProgressBarActions";


type ProgressBarReducer = (state: ProgressBarState | undefined, action: ProgressBarAction) => ProgressBarState

const initialState: ProgressBarState = {
    isVisible: false
}

const progressBarReducer: ProgressBarReducer = (state = initialState, action) => {
    switch (action.type) {
        case "[PROGRESS BAR] SHOW":
            return {
                isVisible: true
            };

        case "[PROGRESS BAR] HIDE":
            return {
                isVisible: false
            }
    }

    return state;
}

export default progressBarReducer;