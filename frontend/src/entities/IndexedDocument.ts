import {AnnotatedText} from "./Annotation";

export interface IndexedDocument {
    title: string;
    url: string;
    body: AnnotatedText;
}