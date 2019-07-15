import {isSearchSettingsContent} from "../SearchSettings";
import {searchResults} from "./realInput.test";
import {isSearchResult} from "../SearchResult";
import {isSnippet} from "../Snippet";
import {isAnnotatedText} from "../Annotation";

describe("SearchSettings content typ guard", () => {
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

        expect(isSearchResult(input))
            .toBe(true);

        for (let snippet of input.snippets) {
            expect(isSnippet(snippet))
                .toBe(true);

            expect(isAnnotatedText(snippet.payload.content))
                .toBe(true);
        }

    });
})