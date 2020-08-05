import {Interval} from "../components/annotations/TextUnitList";

/**
 * Contains necessary information to replicate invalid match
 */
export interface BugReport {
    query: string
    host: string
    collection: string
    documentId: number
    matchInterval: Interval
    description: string
}