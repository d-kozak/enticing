import {SearchResult} from "../entities/SearchResult";

export const SEARCH_RESULTS_NEW = '[SEARCH RESULTS] NEW';

interface NewSearchResultsAction {
    type: typeof SEARCH_RESULTS_NEW
    searchResults: Array<SearchResult>
}

export type SearchResultAction = NewSearchResultsAction

export const newSearchResultsAction = (searchResults: Array<SearchResult>): NewSearchResultsAction => ({
    type: SEARCH_RESULTS_NEW,
    searchResults
});