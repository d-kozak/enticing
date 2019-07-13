import {Payload, payloadSchema} from "./Snippet";
import {validateOrNull} from "./validationUtils";

import * as yup from "yup";

export interface SnippetExtension {
    prefix: Payload
    suffix: Payload
    canExtend: Boolean
}

export const snippetExtensionSchema = yup.object({
    prefix: payloadSchema,
    suffix: payloadSchema,
    canExtent: yup.boolean().required()
})


export function isSnippetExtension(obj: Object): obj is SnippetExtension {
    return validateOrNull(snippetExtensionSchema, obj) !== null;
}