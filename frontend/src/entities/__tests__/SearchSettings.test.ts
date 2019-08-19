import {isSearchSettingsContent} from "../SearchSettings";
import {searchResults} from "./realInput.test";
import {isResultList} from "../ResultList";
import {isSearchResult} from "../SearchResult";
import {isAnnotatedText} from "../Annotation";

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

    it("real backend data", () => {
        const input = searchResults;

        expect(isResultList(input))
            .toBe(true);

        for (let snippet of input.searchResults) {
            expect(isSearchResult(snippet))
                .toBe(true);

            expect(isAnnotatedText(snippet.payload.content))
                .toBe(true);
        }

    });
})