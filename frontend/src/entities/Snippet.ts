import {AnnotatedText, annotatedTextSchema} from "./Annotation";
import {validateOrNull} from "./validationUtils";

import * as yup from "yup";

export interface Payload {
    content: AnnotatedText
}

export const payloadSchema = yup.object({
    content: annotatedTextSchema
});

export function isPayload(obj: Object): obj is Payload {
    return validateOrNull(payloadSchema, obj) !== null;
}

export interface Snippet {
    id: string
    host: string
    collection: string
    documentId: number
    location: number
    size: number
    url: string
    documentTitle: string
    payload: Payload
    canExtend: boolean
}


export const snippetSchema = yup.object({
    host: yup.string().required(),
    collection: yup.string().required(),
    documentId: yup.number().integer().required(),
    location: yup.number().integer().required(),
    size: yup.number().integer().required(),
    url: yup.string().url().required(),
    documentTitle: yup.string().required(),
    payload: payloadSchema,
    canExtend: yup.boolean().required()
})

export function isSnippet(obj: Object): obj is Snippet {
    return validateOrNull(snippetSchema, obj) !== null;
}


