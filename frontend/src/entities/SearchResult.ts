import {validateOrNull} from "./validationUtils";

import * as yup from "yup";
import {TextUnitList, textUnitListSchema} from "../components/annotations/TextUnitList";

export interface Payload {
    content: TextUnitList
}

export const payloadSchema = yup.object({
    content: textUnitListSchema
});

export function isPayload(obj: Object): obj is Payload {
    return validateOrNull(payloadSchema, obj) !== null;
}

export interface SearchResult {
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


export const searchResultSchema = yup.object({
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

export function isSearchResult(obj: Object): obj is SearchResult {
    return validateOrNull(searchResultSchema, obj) !== null;
}


