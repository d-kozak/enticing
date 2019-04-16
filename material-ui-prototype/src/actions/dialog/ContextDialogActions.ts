import {SearchResult} from "../../entities/SearchResult";
import {ThunkResult} from "../RootAction";
import {mockContextExtended, mockContextRequested} from "../../mocks/mockContextApi";
import {SearchResultContext} from "../../entities/SearchResultContext";

interface ContextLoadedAction {
    type: '[CONTEXT DIALOG] CONTEXT LOADED'
    context: SearchResultContext
}

interface ContextDialogClosedAction {
    type: '[CONTEXT DIALOG] CLOSED'
}

interface ContextDialogShowProgressAction {
    type: '[CONTEXT DIALOG] SHOW PROGRESS'
}

interface ContextDialogHideProgressAction {
    type: '[CONTEXT DIALOG] HIDE PROGRESS'
}

interface ContextExtendedAction {
    type: '[CONTEXT DIALOG] CONTEXT EXTENDED'
    context: SearchResultContext
}

export type ContextDialogAction =
    ContextLoadedAction
    | ContextDialogClosedAction
    | ContextExtendedAction
    | ContextDialogShowProgressAction
    | ContextDialogHideProgressAction

export const contextLoadedAction = (context: SearchResultContext): ContextLoadedAction => ({
    type: "[CONTEXT DIALOG] CONTEXT LOADED",
    context
});

export const contextExtendedAction = (context: SearchResultContext): ContextExtendedAction => ({
    type: "[CONTEXT DIALOG] CONTEXT EXTENDED",
    context
});

export const contextDialogClosedAction = (): ContextDialogClosedAction => ({
    type: "[CONTEXT DIALOG] CLOSED"
});

export const contextDialogShowProgressAction = (): ContextDialogShowProgressAction => ({
    type: "[CONTEXT DIALOG] SHOW PROGRESS"
});

export const contextDialogHideProgressAction = (): ContextDialogHideProgressAction => ({
    type: "[CONTEXT DIALOG] HIDE PROGRESS"
});


export const contextDialogRequestedAction = (searchResult: SearchResult): ThunkResult<void> => dispatch => {
    mockContextRequested(searchResult, dispatch);
};

export const contextExtendRequestAction = (context: SearchResultContext): ThunkResult<void> => dispatch => {
    mockContextExtended(context, dispatch)
};
