import {TextMetadata} from "./SearchQuery";

export interface ContextExtensionQuery {
    host: string
    collection: string
    documentId: number
    metadata: TextMetadata
    location: number
    size: number
    extension: number
    defaultIndex: string
    query: string
}