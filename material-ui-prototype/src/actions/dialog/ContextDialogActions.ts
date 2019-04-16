import {SearchResult} from "../../entities/SearchResult";
import {ThunkResult} from "../RootAction";
import {mockContextRequested} from "../../mocks/searchApi";
import {SearchResultContext} from "../../entities/SearchResultContext";

interface ContextLoadedAction {
    type: '[CONTEXT DIALOG] CONTEXT LOADED'
    context: SearchResultContext
}

interface ContextDialogClosedAction {
    type: '[CONTEXT DIALOG] CLOSED'
}


export type ContextDialogAction = ContextLoadedAction | ContextDialogClosedAction

export const contextLoadedAction = (context: SearchResultContext): ContextLoadedAction => ({
    type: "[CONTEXT DIALOG] CONTEXT LOADED",
    context
});

export const contextDialogClosedAction = (): ContextDialogClosedAction => ({
    type: "[CONTEXT DIALOG] CLOSED"
});

export const contextDialogRequestedAction = (searchResult: SearchResult): ThunkResult<void> => dispatch => {
    mockContextRequested(searchResult, dispatch);
};
