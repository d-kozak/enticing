import {rawAnswer, rawAnswer2} from "./realAnswer";
import {transformAnnotatedText} from "../../../../actions/QueryActions";
import {isSearchResult} from "../../../../entities/SearchResult";
import {isSnippet} from "../../../../entities/Snippet";
import {isAnnotatedText} from "../../../../entities/Annotation";
import {preprocessAnnotatedText} from "../PreProcessedAnnotatedText";

it("process real answer", () => {
    for (let input of [rawAnswer, rawAnswer2]) {
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
            preprocessed.dumpJson();

        }
    }


});