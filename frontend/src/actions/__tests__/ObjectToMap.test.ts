import {objectToIntMap} from "../QueryActions";


describe("object to map test", () => {
    it("conversion test", () => {
        const input = {
            1: "foo",
            2: "bar",
            3: "baz"
        }
        const map = objectToIntMap(input)
        expect(map.get(1)).toBe("foo")
        expect(map.get(2)).toBe("bar")
        expect(map.get(3)).toBe("baz")
    })
})