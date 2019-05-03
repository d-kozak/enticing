import {AnnotatedText} from "./Annotation";

export interface SearchResult {
    id: number,
    snippet: AnnotatedText,
    url: string,
    canExtend: boolean
}