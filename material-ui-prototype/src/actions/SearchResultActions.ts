import {SearchResult} from "../entities/SearchResult";

interface NewSearchResultsAction {
    type: '[SEARCH RESULTS] NEW'
    searchResults: Array<SearchResult>
}

export type SearchResultAction = NewSearchResultsAction

export const newSearchResultsAction = (searchResults: Array<SearchResult>): NewSearchResultsAction => ({
    type: "[SEARCH RESULTS] NEW",
    searchResults
});