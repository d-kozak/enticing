import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {loremOneSentece} from "./loremIpsum";
import {searchResultUpdatedAction} from "../actions/SearchResultActions";
import {Payload, Snippet} from "../entities/Snippet";


export const mockContextRequested = (searchResult: Snippet, dispatch: Dispatch) => {
    dispatch(showProgressBarAction())
    setTimeout(() => {
        const updatedResult: Snippet = {
            ...searchResult,
            payload: extendText(searchResult.payload),
            canExtend: Math.random() > 0.3
        };
        dispatch(searchResultUpdatedAction(updatedResult))
        dispatch(hideProgressBarAction());
    }, 2000);
};

const extendText = (annotatedText: Payload): Payload => ({
    content: {
        ...annotatedText.content,
        text: annotatedText.content.text + loremOneSentece
    }
});