export interface WithId {
    /**
     * unique resource identifier that can be used to as a React key when rendering
     */
    id: any
}

export interface PaginatedResult<ContentType> {
    content: Array<ContentType>,
    /**
     * size of the page
     */
    size: number,
    /**
     * page number
     */
    number: number,

    totalPages: number,
    /**
     * total number of items
     */
    totalElements: number
}

export interface PaginatedCollection<ContentType extends WithId> {
    content: {
        [index: number]: ContentType
    }
    totalElements: number
}


export function emptyPaginatedCollection<T extends WithId>(): PaginatedCollection<T> {
    return {
        content: [],
        totalElements: 1e10
    };
}