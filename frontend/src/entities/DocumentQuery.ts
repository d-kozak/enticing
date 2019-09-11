import {TextMetadata} from "./SearchQuery";

export interface DocumentQuery {
    host: string
    collection: string
    documentId: number
    defaultIndex: string,
    metadata: TextMetadata,
    textFormat: "TEXT_UNIT_LIST"
}
