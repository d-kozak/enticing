import {AttributeInfo, CorpusFormat, EntityInfo, IndexInfo, String2StringObjectMap} from "../entities/CorpusFormat";
import {SelectedMetadata} from "../entities/SelectedMetadata";
import {
    ExactEntityDefinition,
    ExactFormatDefinition,
    ExactIndexDefinition,
    IndexDefinition,
    Predefined,
    TextMetadata
} from "../entities/SearchQuery";
import {mapValues} from "lodash";

export function filterCorpusFormat(corpusFormat: CorpusFormat, selectedMetadata: SelectedMetadata | null): CorpusFormat {
    // @Warning should we clone the object where or are we sure that no one dares to modify it?
    if (!selectedMetadata) return corpusFormat;
    const indexes: IndexInfo = {};
    const entities: { [clazz: string]: EntityInfo } = {};

    for (let index of selectedMetadata.indexes) {
        if (corpusFormat.indexes[index] === undefined) {
            console.warn(`index ${index} is not part of selected corpus format`);
            continue;
        }
        indexes[index] = corpusFormat.indexes[index];
    }

    for (let entityName of Object.keys(selectedMetadata.entities)) {
        const entity = selectedMetadata.entities[entityName];
        const entityInfo = corpusFormat.entities[entityName];
        if (entityInfo === undefined) {
            console.warn(`entity ${entityName} is not part of selected corpus format`);
            continue;
        }
        const wantedAttributes: AttributeInfo = {};
        for (let attribute of entity.attributes) {
            if (entityInfo.attributes[attribute] !== undefined) {
                wantedAttributes[attribute] = entityInfo.attributes[attribute];
            } else {
                console.warn(`attribute ${attribute} is not part of selected entity ${entityName}`);
            }
        }
        entities[entityName] = {
            description: entityInfo.description,
            attributes: wantedAttributes
        };
    }

    return {
        corpusName: corpusFormat.corpusName,
        indexes,
        entities,
        defaultIndex: selectedMetadata.defaultIndex
    };
}


export function createMetadataRequest(corpusFormat: CorpusFormat, selectedMetadata: SelectedMetadata | null): TextMetadata {
    if (!selectedMetadata) return new Predefined("all");
    const wantedIndexes = [] as Array<string>;
    for (let index of selectedMetadata.indexes) {
        if (corpusFormat.indexes[index] !== undefined) {
            wantedIndexes.push(index);
        } else {
            console.warn(`index ${index} is not part of selected corpus format`)
        }
    }
    const indexDef: IndexDefinition = wantedIndexes.length == Object.keys(corpusFormat.indexes).length ? new Predefined("all") : new ExactIndexDefinition(wantedIndexes);

    const wantedEntities: { [key: string]: IndexDefinition } = {};
    for (let entityName of Object.keys(selectedMetadata.entities)) {
        const entity = selectedMetadata.entities[entityName];
        const entityInfo = corpusFormat.entities[entityName];
        if (entityInfo === undefined) {
            console.warn(`entity ${entityName} is not part of selected corpus format`);
            continue;
        }
        const wantedAttributes = [] as Array<string>;
        for (let attribute of entity.attributes) {
            if (entityInfo.attributes[attribute] !== undefined) {
                wantedAttributes.push(attribute);
            } else {
                console.warn(`attribute ${attribute} is not part of selected entity ${entityName}`);
            }
        }
        wantedEntities[entityName] = wantedAttributes.length == Object.keys(entityInfo.attributes).length ? new Predefined("all") : new ExactIndexDefinition(wantedAttributes);
    }
    return new ExactFormatDefinition(indexDef, new ExactEntityDefinition(wantedEntities));
}

export function createFullMetadataRequest(corpusFormat: CorpusFormat): TextMetadata {
    const indexes = new ExactIndexDefinition(Object.keys(corpusFormat.indexes));
    const entities = new ExactEntityDefinition(mapValues(corpusFormat.entities, entity => new ExactIndexDefinition(Object.keys(entity.attributes))));
    return new ExactFormatDefinition(indexes, entities)
}