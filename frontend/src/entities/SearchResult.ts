import {Snippet, snippetSchema} from "./Snippet";

import * as yup from "yup";
import {mapValues} from 'lodash';
import {validateOrNull} from "./validationUtils";

export interface SearchResult {
    snippets: Array<Snippet>
    errors: { [key: string]: string }
}

export const searchResultSchema = yup.object({
    snippets: yup.array(snippetSchema),
    errors: yup.lazy(obj => yup.object(
        mapValues(obj, () => yup.string().min(1).required())
    )),
});


export function isSearchResult(obj: Object): obj is SearchResult {
    return validateOrNull(searchResultSchema, obj) !== null;
}
