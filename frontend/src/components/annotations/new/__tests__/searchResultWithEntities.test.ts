import {isSearchResult} from "../../../../entities/SearchResult";
import {isSnippet} from "../../../../entities/Snippet";
import {isAnnotatedText} from "../../../../entities/Annotation";
import {transformAnnotatedText} from "../../../../actions/QueryActions";
import {preprocessAnnotatedText} from "../PreProcessedAnnotatedText";
import {searchResultWithEntities} from "../../../../mocks/searchResultWithEntities";

it("process real answer", () => {
    for (let input of [searchResultWithEntities]) {
        if (!isSearchResult(input)) {
            fail("should be valid");
            return;
        }

        for (let snippet of input.snippets) {
            if (!isSnippet(snippet)) {
                fail("should be valid")
                return;
            }

            if (!isAnnotatedText(snippet.payload.content)) {
                fail("should be valid")
                return;
            }
            transformAnnotatedText(snippet.payload.content)
            const preprocessed = preprocessAnnotatedText(snippet.payload.content)


        }
    }


});