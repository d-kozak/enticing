import {firstResult} from "../../mocks/mockSearchApi";
import {validateAnnotatedText} from "../../entities/Annotation";


it("validate annotation , success case", () => {
    const result = firstResult;
    validateAnnotatedText(result.snippet);
});