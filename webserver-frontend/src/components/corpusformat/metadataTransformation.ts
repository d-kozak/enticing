import {SelectedMetadata} from "../../entities/SelectedMetadata";

export const splitMetadata = (metadata: SelectedMetadata | undefined): [Array<string>, Array<string>] => {
    if (!metadata) return [[], []];

    const attributes = [] as Array<string>;

    for (let entityName of Object.keys(metadata.entities)) {
        attributes.push(entityName);
        for (let attribute of metadata.entities[entityName].attributes) {
            attributes.push(`${entityName}/${attribute}`);
        }
    }

    return [metadata.indexes, attributes];
};


export const createMetadata = (indexes: Array<string>, attributes: Array<string>, colors: { [entity: string]: string }, defaultIndex: string): SelectedMetadata => {
    const entities: { [key: string]: { attributes: Array<string>, color: string } } = {};
    for (let item of attributes) {
        const [entity, attribute] = item.split("/");
        if (entities[entity]) {
            entities[entity].attributes.push(attribute);
        } else {
            const newEntity = {attributes: [] as Array<string>, color: colors[entity] || 'purple'};
            if (attribute) {
                newEntity.attributes.push(attribute);
            }
            entities[entity] = newEntity;
        }
    }
    return {
        indexes,
        entities,
        defaultIndex
    }
};
