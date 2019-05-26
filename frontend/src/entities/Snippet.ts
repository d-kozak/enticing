import {AnnotatedText} from "./Annotation";

export interface Snippet {
    id: number,
    docId: string, // UUID - not specified exactly, because it is not part of standard javascript library and the frontent does not really need to understand it anyway
    location: number,
    size: number,
    snippet: AnnotatedText,
    url: string,
    canExtend: boolean
}