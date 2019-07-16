import {realSnippet} from "../../../../mocks/realSnippet";
import {preprocessAnnotatedText, QueryMatch} from "../PreProcessedAnnotatedText";
import {firstResult, secondResult, thirdResult} from "../../../../mocks/mockSearchApi";

it("ed sheeran", () => {
    const input = firstResult;

    const preprocessed = preprocessAnnotatedText(input.payload.content);

    expect(preprocessed.content.length)
        .toBe(2)
});

it("donald", () => {
    const input = secondResult;

    const preprocessed = preprocessAnnotatedText(input.payload.content);

    expect(preprocessed.content.length)
        .toBe(2);
});

it("milos", () => {
    const input = thirdResult;

    const preprocessed = preprocessAnnotatedText(input.payload.content);

    expect(preprocessed.content.length)
        .toBe(2)

});

it("real world snippet", () => {
    const input = realSnippet;

    const preprocessed = preprocessAnnotatedText(input.payload.content);

    expect(preprocessed.content.length)
        .toBe(4);

    expect(preprocessed.content[0] instanceof QueryMatch)
        .toBe(true);
});