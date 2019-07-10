import {AnnotatedText} from "./Annotation";

export interface Match {
    id: string;
    collection: string;
    documentId: number,
    location: number,
    size: number,
    url: string,
    documentTitle: string,
    payload: AnnotatedText,
    canExtend: boolean
}