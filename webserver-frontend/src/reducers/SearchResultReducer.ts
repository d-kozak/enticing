import {SearchResultsState, SearchStatistics} from "../ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {SearchResult} from "../entities/SearchResult";
import {CorpusFormat} from "../entities/CorpusFormat";

export interface SearchResultBatch {
    query: string,
    searchResults: Array<SearchResult>,
    corpusFormat: CorpusFormat,
    moreResultsAvailable: boolean,
    statistics?: SearchStatistics
}

const {reducer, actions} = createSlice({
    slice: 'searchResults',
    initialState: {
        query: "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url",
        snippetIds: [],
        snippetsById: {},
        corpusFormat: null,
        moreResultsAvailable: false,
        snippetConfig: {
            viewFormat: "MINIMIZED"
        }
    } as SearchResultsState,
    reducers: {
        newSearchResults: (state: SearchResultsState, {payload}: PayloadAction<SearchResultBatch>) => {
            state.corpusFormat = payload.corpusFormat;
            state.moreResultsAvailable = payload.moreResultsAvailable;
            state.snippetIds = [];
            state.snippetsById = {};
            state.query = payload.query;
            state.statistics = payload.statistics;
            for (let snippet of payload.searchResults) {
                state.snippetsById[snippet.id] = snippet;
                state.snippetIds.push(snippet.id);
            }
        },
        appendMoreSearchResults: (state: SearchResultsState, {payload}: PayloadAction<{ searchResults: Array<SearchResult>, hasMore: boolean, statistics?: SearchStatistics }>) => {
            state.moreResultsAvailable = payload.hasMore;
            state.statistics = payload.statistics;
            for (let snippet of payload.searchResults) {
                state.snippetsById[snippet.id] = snippet;
                state.snippetIds.push(snippet.id);
            }
        },
        updateSearchResult: (state: SearchResultsState, {payload}: PayloadAction<SearchResult>) => {
            state.snippetsById[payload.id] = payload;
        },
        setMoreResultsAvailable: (state: SearchResultsState, {payload}: PayloadAction<boolean>) => {
            state.moreResultsAvailable = payload;
        }
    }
});

export const {newSearchResults, updateSearchResult, setMoreResultsAvailable, appendMoreSearchResults} = actions;
export default reducer;

