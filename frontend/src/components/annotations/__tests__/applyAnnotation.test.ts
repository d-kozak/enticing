import {firstResult} from "../../../mocks/mockSearchApi";
import {validateAnnotatedText} from "../../../entities/Annotation";

describe("validate annotation ", () => {
    it("success case", () => {
        const result = firstResult;
        validateAnnotatedText(result.snippet);
    });
});
