import * as yup from "yup";
import {mapValues} from "lodash";
import {validateOrNull} from "./validationUtils";


type String2StringObjectMap = { [key: string]: string }

export type IndexInfo = String2StringObjectMap

export type AttributesInfo = String2StringObjectMap

export interface CorpusFormat {
    corpusName: string,
    indexes: IndexInfo,
    entities: { [clazz: string]: AttributesInfo }
}

const string2stringObjectMapSchema = yup.lazy(obj => yup.object(
    mapValues(obj, () => yup.string().required())
))

export const corpusFormatSchema = yup.object({
    corpusName: yup.string(),
    indexes: string2stringObjectMapSchema,
    entities: yup.lazy(obj => yup.object(
        mapValues(obj, () => string2stringObjectMapSchema)
    ))
});

export function isCorpusFormat(obj: object): obj is CorpusFormat {
    return validateOrNull(corpusFormatSchema, obj) !== null
}