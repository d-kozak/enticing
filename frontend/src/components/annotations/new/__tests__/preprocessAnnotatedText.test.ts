import {realSnippet} from "../../../../mocks/realSnippet";
import {preprocessAnnotatedText, QueryMatch} from "../PreProcessedAnnotatedText";
import {firstResult, secondResult, thirdResult} from "../../../../mocks/mockSearchApi";
import {loremIpsumLong} from "../../../../mocks/loremIpsum";
import {EdSheeran} from "../../../../mocks/mockAnnotations";
import {MatchedRegion} from "../../../../entities/Annotation";
import {Payload} from "../../../../entities/Snippet";

it("ed sheeran", () => {
    const input = firstResult;

    const preprocessed = preprocessAnnotatedText(input.payload.content);

    expect(preprocessed.content.length)
        .toBe(4)
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

it("empty query mapping", () => {
    const emptyQueryMapping: Payload = {
        content: {
            text: loremIpsumLong,
            annotations: {
                "ed": EdSheeran
            },
            positions: [{
                annotationId: "ed", match: new MatchedRegion(99, 10), subAnnotations: []
            }],
            queryMapping: []
        }
    }

    const preprocessed = preprocessAnnotatedText(emptyQueryMapping.content);

    expect(preprocessed.content.length)
        .toBe(3)

});