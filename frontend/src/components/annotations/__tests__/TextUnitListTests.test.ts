import {isTextUnitList, TextUnitList, Word} from "../TextUnitList";

describe("validation tests", () => {
    it("empty text", () => {
        const text: TextUnitList = new TextUnitList([]);
        expect(isTextUnitList(text)).toBe(true);
    });

    it("simple text", () => {
        const text: TextUnitList = new TextUnitList([new Word(["ahoj", "cau"])]);
        expect(isTextUnitList(text)).toBe(true);
    });
});

