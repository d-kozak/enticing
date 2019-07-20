import {isSearchResult} from "../../../entities/SearchResult";
import {parseNewAnnotatedText} from "../NewAnnotatedText";
import {realResponse} from "../../../mocks/realResponse";


it("process real response", () => {
    const response = realResponse

    if (!isSearchResult(response)) {
        throw `Invalid search result ${JSON.stringify(response, null, 2)}`;
    }
    for (let error in response.errors) {
        console.log(`Error from ${error}: ${response.errors[error]}`);
    }
    for (let snippet of response.snippets) {
        snippet.id = `${snippet.host}:${snippet.collection}:${snippet.documentId}`
    }
    const parsedText = response.snippets.map(
        item => parseNewAnnotatedText(item.payload.content)
    ).filter(item => item != null)

    expect(parsedText.length)
        .toBe(response.snippets.length)
})


