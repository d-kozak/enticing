import {SearchResult} from "../entities/SearchResult";
import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {SearchResultContext} from "../entities/SearchResultContext";
import {
    contextDialogHideProgressAction,
    contextDialogShowProgressAction,
    contextExtendedAction,
    contextLoadedAction
} from "../actions/dialog/ContextDialogActions";
import {AnnotatedText} from "../entities/Annotation";
import {loremIpsumShort} from "./loremIpsum";


export const mockContextRequested = (searchResult: SearchResult, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const context: SearchResultContext = {
            text: extendText(searchResult.snippet),
            url: searchResult.url,
            canExtend: true
        };
        dispatch(hideProgressBarAction());
        dispatch(contextLoadedAction(context));
    }, 2000);
};

export const mockContextExtended = (context: SearchResultContext, dispatch: Dispatch) => {
    dispatch(contextDialogShowProgressAction())
    setTimeout(() => {
        const canExtend = Math.random() > 0.3;
        const newContext: SearchResultContext = {
            text: extendText(context.text),
            url: context.url,
            canExtend: canExtend
        };
        dispatch(contextDialogHideProgressAction())
        dispatch(contextExtendedAction(newContext));
    }, 2000);
};


const extendText = (annotatedText: AnnotatedText): AnnotatedText => ({
    ...annotatedText,
    text: annotatedText.text + loremIpsumShort
});