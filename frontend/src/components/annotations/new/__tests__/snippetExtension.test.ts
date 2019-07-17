import {snippetExtension, snippetToExtend} from "./snippetExtension";
import {isSnippetExtension} from "../../../../entities/SnippetExtension";
import {isAnnotatedText} from "../../../../entities/Annotation";
import {transformAnnotatedText} from "../../../../actions/QueryActions";
import {isSnippet} from "../../../../entities/Snippet";
import {mergeSnippet} from "../../../../actions/ContextActions";
import {preprocessAnnotatedText} from "../PreProcessedAnnotatedText";

it("process snippet extension", () => {
    const snippet = snippetToExtend;
    const extension = snippetExtension;

    if (!isSnippet(snippet)) {
        fail("should be valid snippet");
        return;
    }

    if (!isAnnotatedText(snippet.payload.content)) {
        fail("snippet should have valid annotated text");
        return;
    }
    transformAnnotatedText(snippet.payload.content);


    if (!isSnippetExtension(extension)) {
        fail("should be valid snippet extension");
        return;
    }

    for (let text of [snippetExtension.prefix, snippetExtension.suffix]) {
        if (!isAnnotatedText(text.content)) {
            fail("should be valid annotated text");
            return;
        }
        transformAnnotatedText(text.content);
    }

    const merged = mergeSnippet(snippet, extension);

    const preprocessed = preprocessAnnotatedText(merged.payload.content);

    expect(preprocessed.content.length)
        .toBe(51);


});
