import {AnnotatedText} from "./Annotation";

export interface SearchResultContext {
    url: string
    text: AnnotatedText
    canExtend: boolean
}