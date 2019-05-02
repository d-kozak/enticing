export const PROGRESS_BAR_SHOW = '[PROGRESS BAR] SHOW';
export const PROGRESS_BAR_HIDE = '[PROGRESS BAR] HIDE';

interface ShowProgressBarAction {
    type: typeof PROGRESS_BAR_SHOW
}

interface HideProgressBarAction {
    type: typeof PROGRESS_BAR_HIDE
}

export type ProgressBarAction = ShowProgressBarAction | HideProgressBarAction

export const showProgressBarAction = (): ShowProgressBarAction => ({
    type: PROGRESS_BAR_SHOW
});

export const hideProgressBarAction = (): HideProgressBarAction => ({
    type: PROGRESS_BAR_HIDE
});