import {generate} from "shortid";
import {AddNewItemOptions, PaginatedCollection, PaginatedResult, WithId} from "./pagination";


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

function add<T extends WithId>(collection: PaginatedCollection<T>, item: T, options: Partial<AddNewItemOptions<T>> = {}) {
    processElement(item, options);
    if (options.nestedCollectionName) {
        const prev = collection.elements[item.id];
        // @ts-ignore
        if (prev) {
            // @ts-ignore
            item[options.nestedCollectionName] = prev[options.nestedCollectionName]
        }
    }
    collection.elements[item.id] = item;
    collection.totalElements++;
}

function refresh<T extends WithId>(collection: PaginatedCollection<T>, items: Array<T>, options: Partial<AddNewItemOptions<T>> = {}) {
    clear(collection);
    for (let i = 0; i < items.length; i++) {
        const item = items[i];
        processElement(item, options);
        collection.elements[item.id] = item
        collection.index[i] = item.id
    }
    collection.totalElements = items.length;
}

function addAll<T extends WithId>(collection: PaginatedCollection<T>, newItems: PaginatedResult<T>, options: Partial<AddNewItemOptions<T>> = {}) {
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

function remove<T extends WithId>(collection: PaginatedCollection<T>, item: T) {
    delete collection.elements[item.id];
    collection.totalElements--;
}

function clear<T extends WithId>(collection: PaginatedCollection<T>) {
    collection.index = {}
    collection.elements = {}
    collection.totalElements = 1e10;
}

function emptyCollection<Element extends WithId>(): PaginatedCollection<Element> {
    return {
        index: {},
        elements: {},
        totalElements: 1e10
    };
}

export default {
    add, addAll, refresh, remove, clear, emptyCollection
};