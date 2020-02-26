import {Payload, payloadSchema} from "./SearchResult";
import {validateOrNull} from "./validationUtils";

import * as yup from "yup";

export interface SnippetExtension {
    prefix: Payload
    suffix: Payload
    canExtend: boolean
}

export const snippetExtensionSchema = yup.object({
    prefix: payloadSchema,
    suffix: payloadSchema,
    canExtend: yup.boolean().required()
})


export function isSnippetExtension(obj: Object): obj is SnippetExtension {
    return validateOrNull(snippetExtensionSchema, obj) !== null;
}