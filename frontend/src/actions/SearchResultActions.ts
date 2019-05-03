import {SearchResult} from "../entities/SearchResult";

export const SEARCH_RESULTS_NEW = '[SEARCH RESULT] NEW';
export const SEARCH_RESULT_UPDATED = '[SEARCH RESULT] ITEM UPDATED';

interface NewSearchResultsAction {
    type: typeof SEARCH_RESULTS_NEW
    searchResults: Array<SearchResult>
}

interface SearchResultUpdatedAction {
    type: typeof SEARCH_RESULT_UPDATED,
    searchResult: SearchResult
}

export type SearchResultAction = NewSearchResultsAction | SearchResultUpdatedAction

export const newSearchResultsAction = (searchResults: Array<SearchResult>): NewSearchResultsAction => ({
    type: SEARCH_RESULTS_NEW,
    searchResults
});

export const searchResultUpdatedAction = (searchResult: SearchResult): SearchResultUpdatedAction => ({
    type: SEARCH_RESULT_UPDATED,
    searchResult
});