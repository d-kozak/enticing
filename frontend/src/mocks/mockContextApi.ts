import {SearchResult} from "../entities/SearchResult";
import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {AnnotatedText} from "../entities/Annotation";
import {loremOneSentece} from "./loremIpsum";
import {searchResultUpdatedAction} from "../actions/SearchResultActions";


export const mockContextRequested = (searchResult: SearchResult, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const updatedResult: SearchResult = {
            ...searchResult,
            id: searchResult.id,
            url: searchResult.url,
            snippet: extendText(searchResult.snippet),
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