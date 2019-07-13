import {QueryMapping, queryMappingSchema} from "./Annotation";
import {Payload, payloadSchema} from "./Snippet";
import * as yup from "yup";
import {validateOrNull} from "./validationUtils";

export interface FullDocument {
    host: string
    collection: string
    documentId: number
    title: string
    url: string
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
    query: yup.string(),
    queryMapping: yup.array(queryMappingSchema)
})

export function isDocument(obj: Object): obj is FullDocument {
    return validateOrNull(documentSchema, obj) !== null;
}


