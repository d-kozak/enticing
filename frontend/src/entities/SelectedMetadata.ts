export interface SelectedMetadata {
    indexes: Array<string>,
    entities: { [key: string]: { attributes: Array<string> } },
    defaultIndex: string
}