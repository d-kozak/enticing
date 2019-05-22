import {firstResult} from "../../../mocks/mockSearchApi";
import {enrichText} from "../applyAnnotations";

describe("enrich text", () => {
    it("mock api processing", () => {
        const edSheeran = firstResult;
        const processed = enrichText(edSheeran.snippet);
    })
});