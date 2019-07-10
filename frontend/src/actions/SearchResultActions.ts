import {Match} from "../entities/Snippet";

export const SEARCH_RESULTS_NEW = '[SEARCH RESULT] NEW';
export const SEARCH_RESULT_UPDATED = '[SEARCH RESULT] ITEM UPDATED';

interface NewSearchResultsAction {
    type: typeof SEARCH_RESULTS_NEW
    snippets: Array<Match>
}

interface SearchResultUpdatedAction {
    type: typeof SEARCH_RESULT_UPDATED,
    snippet: Match
}

export type SearchResultAction = NewSearchResultsAction | SearchResultUpdatedAction

export const newSearchResultsAction = (snippets: Array<Match>): NewSearchResultsAction => ({
    type: SEARCH_RESULTS_NEW,
    snippets
});

export const searchResultUpdatedAction = (snippet: Match): SearchResultUpdatedAction => ({
    type: SEARCH_RESULT_UPDATED,
    snippet
});