import {MatchedRegion} from "../Annotation";

it("how to parse string into specific JSON class", () => {
    const input = JSON.stringify({from: 10, size: 11})
    const parsed = JSON.parse(input)
    const matchedRegion: MatchedRegion = Object.assign(new MatchedRegion(), parsed)

    expect(matchedRegion.to).toBe(21)

})