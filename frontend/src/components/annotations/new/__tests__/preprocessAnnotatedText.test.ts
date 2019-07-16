import {realSnippet} from "../../../../mocks/realSnippet";
import {preprocessAnnotatedText} from "../PreProcessedAnnotatedText";

it("real world snippet", () => {
    const input = realSnippet;

    const preprocessed = preprocessAnnotatedText(input.payload.content);

    console.log(preprocessed.toJson())
});