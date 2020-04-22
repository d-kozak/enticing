export interface WithId {
    /**
     * unique resource identifier that can be used to as a React key when rendering
     */
    id: string
}

export interface PaginatedResult<Element> {
    content: Array<Element>,
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

export interface PaginatedCollection<Element extends WithId> {
    index: {
        [i: number]: string
    }
    elements: {
        [index: string]: Element
    }
    totalElements: number
}


export function clearCollection<T extends WithId>(collection: PaginatedCollection<T>) {
    collection.index = {}
    collection.elements = {}
    collection.totalElements = 1e10;
}

export function emptyPaginatedCollection<Element extends WithId>(): PaginatedCollection<Element> {
    return {
        index: {},
        elements: {},
        totalElements: 1e10
    };
}