import {generate} from "shortid";

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

export interface AddNewItemsExtraOptions<T> {
    generateId: boolean,
    stringifyId: boolean,
    nestedCollectionName: string,
    forEachElem: (elem: T) => void
}

export function addNewItemsToCollection<T extends WithId>(collection: PaginatedCollection<T>, newItems: PaginatedResult<T>, options: Partial<AddNewItemsExtraOptions<T>> = {}) {
    const offset = newItems.number * newItems.size;
    for (let i = 0; i < newItems.content.length; i++) {
        const elem = newItems.content[i];
        if (options.generateId)
            elem.id = generate();
        if (options.stringifyId)
            elem.id = elem.id.toString();
        if (options.nestedCollectionName) {
            // @ts-ignore
            elem[options.nestedCollectionName] = emptyPaginatedCollection();
        }
        if (options.forEachElem)
            options.forEachElem(elem);
        collection.index[offset + i] = elem.id;
        collection.elements[elem.id] = elem;
    }

    for (let i = newItems.content.length; i < newItems.size; i++) {
        delete collection.index[offset + i]
    }

    collection.totalElements = newItems.totalElements
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