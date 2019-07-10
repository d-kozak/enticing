import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {AnnotatedText} from "../entities/Annotation";
import {loremOneSentece} from "./loremIpsum";
import {searchResultUpdatedAction} from "../actions/SearchResultActions";
import {Match} from "../entities/Snippet";


export const mockContextRequested = (searchResult: Match, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const updatedResult: Match = {
            ...searchResult,
            payload: extendText(searchResult.payload),
            canExtend: Math.random() > 0.3
        };
        dispatch(searchResultUpdatedAction(updatedResult))
        dispatch(hideProgressBarAction());
    }, 2000);
};

const extendText = (annotatedText: AnnotatedText): AnnotatedText => ({
    ...annotatedText,
    text: annotatedText.text + loremOneSentece
});