import {SearchResult} from "../entities/SearchResult";
import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {SearchResultContext} from "../entities/SearchResultContext";
import {AnnotatedText} from "../entities/Annotation";
import {loremOneSentece} from "./loremIpsum";


export const mockContextRequested = (searchResult: SearchResult, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const context: SearchResultContext = {
            text: extendText(searchResult.snippet),
            url: searchResult.url,
            canExtend: Math.random() > 0.3
        };
        dispatch(hideProgressBarAction());
    }, 2000);
};

const extendText = (annotatedText: AnnotatedText): AnnotatedText => ({
    ...annotatedText,
    text: annotatedText.text + loremOneSentece
});