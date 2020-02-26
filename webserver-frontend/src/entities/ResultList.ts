import {SearchResult, searchResultSchema} from "./SearchResult";

import * as yup from "yup";
import {mapValues} from 'lodash';
import {validateOrNull} from "./validationUtils";

export interface ResultList {
    searchResults: Array<SearchResult>
    errors: { [key: string]: string }
}

export const resultListSchema = yup.object({
    searchResults: yup.array(searchResultSchema),
    errors: yup.lazy(obj => yup.object(
        mapValues(obj, () => yup.string().min(1).required())
    )),
});


export function isResultList(obj: Object): obj is ResultList {
    return validateOrNull(resultListSchema, obj) !== null;
}
