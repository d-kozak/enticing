import {createMetadata} from "../metadataTransformation";
import {SelectedMetadata} from "../../../entities/SelectedMetadata";


it("create metadata test", () => {

    const indexes = ["lemma", "token", "nertag"];
    const entities = ["person", "person/name", "person/age", "location", "date/month", "date/day"];

    const metadata = createMetadata(indexes, entities, {person: 'blue', location: 'green', date: 'red'}, "token");
    expect(metadata)
        .toEqual({
            indexes: ["lemma", "token", "nertag"],
            entities: {
                "person": {
                    attributes: ["name", "age"],
                    color: "blue"
                },
                "location": {
                    attributes: [],
                    color: "green"
                },
                "date": {
                    attributes: ["month", "day"],
                    color: "red"
                }
            },
            defaultIndex: "token"
        } as SelectedMetadata);
});