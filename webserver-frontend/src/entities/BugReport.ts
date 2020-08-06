import {Interval} from "../components/annotations/TextUnitList";

/**
 * Contains necessary information to replicate invalid match
 */
export interface BugReport {
    query: string
    filterOverlaps: boolean
    host: string
    collection: string
    documentId: number
    documentTitle: string
    matchInterval: Interval
    description: string
}