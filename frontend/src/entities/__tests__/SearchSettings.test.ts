import {isSearchSettingsContent} from "../SearchSettings";

describe("SearchSettings content type guard", () => {
    it("should pass", () => {
        const input = {
            "name": "Wikipedia 2018",
            "annotationDataServer": "server1.com:42/foo",
            "annotationServer": "localhost:666",
            "servers": [
                "localhost:4200"
            ]
        }
        expect(isSearchSettingsContent(input))
            .toBe(true)
    });

    it("should fail missing name", () => {
        const input = {
            "annotationDataServer": "server1.com:42/foo",
            "annotationServer": "localhost:666",
            "servers": [
                "localhost:4200"
            ]
        }
        expect(isSearchSettingsContent(input))
            .toBe(false)
    });
})