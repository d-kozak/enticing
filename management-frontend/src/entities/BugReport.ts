/**
 * Contains necessary information to replicate invalid match
 */
export interface BugReport {
    id: string
    query: string
    filterOverlaps: boolean
    host: string
    collection: string
    documentId: number
    documentTitle: string
    matchInterval: Interval
    description: string
}

export interface Interval {
    from: number,
    to: number
}