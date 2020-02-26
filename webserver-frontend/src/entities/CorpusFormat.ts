import * as yup from "yup";
import {mapValues} from "lodash";
import {validateOrNull} from "./validationUtils";


export type String2StringObjectMap = { [key: string]: string }

export type IndexInfo = String2StringObjectMap

export type AttributeInfo = { [key: string]: { description: string } };
export type EntityInfo = {
    description: string
    attributes: AttributeInfo
}

export interface CorpusFormat {
    corpusName: string,
    indexes: IndexInfo,
    entities: { [clazz: string]: EntityInfo },
    defaultIndex?: string
}

export const corpusFormatSchema = yup.object({
    corpusName: yup.string(),
    indexes: yup.object(),
    entities: yup.object({
            description: yup.string(),
        attributes: yup.object()
        }
    )
});


export function isCorpusFormat(obj: object): obj is CorpusFormat {
    return validateOrNull(corpusFormatSchema, obj) !== null
}