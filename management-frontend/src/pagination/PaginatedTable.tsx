export interface Column<T> {
    id: string,
    label: string,

    format(input: T): string
}

export interface TableData<Content> {
    columns: Array<Column<any>>
    extraColumns?: (input: Content) => any
}