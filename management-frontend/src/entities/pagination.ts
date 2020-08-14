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

export interface AddNewItemOptions<T> {
    generateId: boolean,
    stringifyId: boolean,
    nestedCollectionName: string,
    forEachElem: (elem: T) => void
}

export namespace PaginatedCollections {
    function processElement<T extends WithId>(elem: T, options: Partial<AddNewItemOptions<T>>) {
        if (options.generateId)
            elem.id = generate();
        if (options.stringifyId)
            elem.id = elem.id.toString();
        if (options.nestedCollectionName) {
            // @ts-ignore
            elem[options.nestedCollectionName] = emptyCollection();
        }
        if (options.forEachElem)
            options.forEachElem(elem);
    }

    export function add<T extends WithId>(collection: PaginatedCollection<T>, item: T, options: Partial<AddNewItemOptions<T>> = {}) {
        processElement(item, options);
        collection.elements[item.id] = item;
        collection.totalElements++;
    }

    export function refresh<T extends WithId>(collection: PaginatedCollection<T>, items: Array<T>, options: Partial<AddNewItemOptions<T>> = {}) {
        clear(collection);
        for (let i = 0; i < items.length; i++) {
            const item = items[i];
            processElement(item, options);
            collection.elements[item.id] = item
            collection.index[i] = item.id
        }
        collection.totalElements = items.length;
    }

    export function addAll<T extends WithId>(collection: PaginatedCollection<T>, newItems: PaginatedResult<T>, options: Partial<AddNewItemOptions<T>> = {}) {
        const offset = newItems.number * newItems.size;
        for (let i = 0; i < newItems.content.length; i++) {
            const elem = newItems.content[i];
            processElement(elem, options);
            collection.index[offset + i] = elem.id;
            collection.elements[elem.id] = elem;
        }

        for (let i = newItems.content.length; i < newItems.size; i++) {
            delete collection.index[offset + i]
        }

        collection.totalElements = newItems.totalElements
    }

    export function remove<T extends WithId>(collection: PaginatedCollection<T>, item: T) {
        delete collection.elements[item.id];
        collection.totalElements--;
    }

    export function clear<T extends WithId>(collection: PaginatedCollection<T>) {
        collection.index = {}
        collection.elements = {}
        collection.totalElements = 1e10;
    }

    export function emptyCollection<Element extends WithId>(): PaginatedCollection<Element> {
        return {
            index: {},
            elements: {},
            totalElements: 1e10
        };
    }
}

