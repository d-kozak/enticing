import * as yup from "yup";
import {mapValues} from "lodash";
import {validateOrNull} from "./validationUtils";


export type String2StringObjectMap = { [key: string]: string }

export type IndexInfo = String2StringObjectMap

export type EntityInfo = {
    description: string
    attributes: String2StringObjectMap
}

export interface CorpusFormat {
    corpusName: string,
    indexes: IndexInfo,
    entities: { [clazz: string]: EntityInfo },
    defaultIndex?: string
}

const string2stringObjectMapSchema = yup.lazy(obj => yup.object(
    mapValues(obj, () => yup.string().min(0))
))

export const corpusFormatSchema = yup.object({
    corpusName: yup.string(),
    indexes: string2stringObjectMapSchema,
    entities: yup.object({
            description: yup.string(),
            attributes: yup.lazy(obj => yup.object(
                mapValues(obj, () => string2stringObjectMapSchema)
            ))
        }
    )
});


export function isCorpusFormat(obj: object): obj is CorpusFormat {
    return validateOrNull(corpusFormatSchema, obj) !== null
}