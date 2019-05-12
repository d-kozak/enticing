import {AnnotatedText} from "./Annotation";

export interface SearchResult {
    id: number,
    docId: number,
    location: number,
    size: number,
    snippet: AnnotatedText,
    url: string,
    canExtend: boolean
}