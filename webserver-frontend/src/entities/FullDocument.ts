import {QueryMapping, queryMappingSchema} from "./Annotation";
import {Payload, payloadSchema} from "./SearchResult";
import * as yup from "yup";
import {validateOrNull} from "./validationUtils";

export interface DocumentDebugInfo {
    host: string,
    collection: string,
    documentId: string,
    title: string,
    url: string,
}

export interface FullDocument extends DocumentDebugInfo {
    payload: Payload
    query?: string
    queryMapping: Array<QueryMapping>
}

const documentSchema = yup.object({
    host: yup.string().required(),
    collection: yup.string().required(),
    documentId: yup.number().integer().required(),
    title: yup.string().required(),
    url: yup.string().url().required(),
    payload: payloadSchema,
    query: yup.string().nullable(),
    queryMapping: yup.array(queryMappingSchema)
});

export function isDocument(obj: Object): obj is FullDocument {
    return validateOrNull(documentSchema, obj) !== null;
}


