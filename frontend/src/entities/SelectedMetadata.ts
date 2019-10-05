export interface SelectedMetadata {
    indexes: Array<string>,
    entities: { [key: string]: { attributes: Array<string>, color: string } },
    defaultIndex: string
}