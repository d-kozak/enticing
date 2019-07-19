import {Snippet} from "../entities/Snippet";
import {CorpusFormat} from "../entities/CorpusFormat";

export const SEARCH_RESULTS_NEW = '[SEARCH RESULT] NEW';
export const SEARCH_RESULT_UPDATED = '[SEARCH RESULT] ITEM UPDATED';

interface NewSearchResultsAction {
    type: typeof SEARCH_RESULTS_NEW
    snippets: Array<Snippet>,
    corpusFormat: CorpusFormat
}

interface SearchResultUpdatedAction {
    type: typeof SEARCH_RESULT_UPDATED,
    snippet: Snippet
}

export type SearchResultAction = NewSearchResultsAction | SearchResultUpdatedAction

export const newSearchResultsAction = (snippets: Array<Snippet>, corpusFormat: CorpusFormat): NewSearchResultsAction => ({
    type: SEARCH_RESULTS_NEW,
    snippets,
    corpusFormat
});

export const searchResultUpdatedAction = (snippet: Snippet): SearchResultUpdatedAction => ({
    type: SEARCH_RESULT_UPDATED,
    snippet
});