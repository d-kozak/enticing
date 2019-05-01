interface ShowProgressBarAction {
    type: '[PROGRESS BAR] SHOW'
}

interface HideProgressBarAction {
    type: '[PROGRESS BAR] HIDE'
}

export type ProgressBarAction = ShowProgressBarAction | HideProgressBarAction

export const showProgressBarAction = (): ShowProgressBarAction => ({
    type: "[PROGRESS BAR] SHOW"
});

export const hideProgressBarAction = (): HideProgressBarAction => ({
    type: "[PROGRESS BAR] HIDE"
});