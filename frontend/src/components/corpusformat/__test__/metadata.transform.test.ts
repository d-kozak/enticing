import {createMetadata} from "../metadataTransformation";
import {SelectedMetadata} from "../../../entities/SelectedMetadata";


it("create metadata test", () => {

    const indexes = ["lemma", "token", "nertag"];
    const entities = ["person", "person/name", "person/age", "location", "date/month", "date/day"];

    const metadata = createMetadata(indexes, entities, "token");
    expect(metadata)
        .toEqual({
            indexes: ["lemma", "token", "nertag"],
            entities: {
                "person": {
                    attributes: ["name", "age"]
                },
                "location": {
                    attributes: []
                },
                "date": {
                    attributes: ["month", "day"]
                }
            },
            defaultIndex: "token"
        } as SelectedMetadata);
});