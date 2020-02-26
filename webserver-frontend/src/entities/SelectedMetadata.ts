export interface SelectedMetadata {
    indexes: Array<string>,
    entities: { [key: string]: { attributes: Array<string>, color: string } },
    defaultIndex: string
}

export function isSelectedMetadata(obj: Object): obj is SelectedMetadata {
    const data = obj as SelectedMetadata;
    if (!Array.isArray(data.indexes)) return false;
    if (typeof data.defaultIndex !== 'string') return false;

    for (let entity of Object.values(data.entities)) {
        if (typeof entity.color !== 'string') return false;
        if (!Array.isArray(entity.attributes)) return false;
    }

    return true;
}
