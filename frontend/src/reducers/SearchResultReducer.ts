import {SearchResultsState, SearchStatistics} from "../ApplicationState";
import {createSlice, PayloadAction} from "redux-starter-kit";
import {SearchResult} from "../entities/SearchResult";
import {ThunkResult} from "../actions/RootActions";
import {emptyTextUnitList, parseNewAnnotatedText} from "../components/annotations/TextUnitList";
import {PerfTimer} from "../utils/perf";
import {CorpusFormat} from "../entities/CorpusFormat";

export interface SearchResultBatch {
    searchResults: Array<SearchResult>,
    corpusFormat: CorpusFormat,
    moreResultsAvailable: boolean,
    statistics: SearchStatistics
}

const {reducer, actions} = createSlice({
    slice: 'searchResults',
    initialState: {
        snippetIds: [],
        snippetsById: {},
        corpusFormat: null,
        moreResultsAvailable: false,
    } as SearchResultsState,
    reducers: {
        newSearchResults: (state: SearchResultsState, {payload}: PayloadAction<SearchResultBatch>) => {
            state.corpusFormat = payload.corpusFormat;
            state.moreResultsAvailable = payload.moreResultsAvailable;
            state.snippetIds = [];
            state.snippetsById = {};
            state.statistics = payload.statistics;
            for (let snippet of payload.searchResults) {
                state.snippetsById[snippet.id] = snippet;
                state.snippetIds.push(snippet.id);
            }
        },
        appendMoreSearchResults: (state: SearchResultsState, {payload}: PayloadAction<{ searchResults: Array<SearchResult>, hasMore: boolean, statistics: SearchStatistics }>) => {
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

let counter = 1;

export const parseSearchResultRequest = (searchResult: SearchResult): ThunkResult<void> => (dispatch) => {
    console.log(`parsing called ${counter++} times`);
    if (searchResult.payload.parsedContent) {
        console.error(`${searchResult.id} is already parsed, nothing to do`);
        return;
    }
    const timer = new PerfTimer('ParseSearchResult');
    timer.sample('starting...');
    const parsed = parseNewAnnotatedText(searchResult.payload.content);
    timer.finish();
    if (parsed != null) {
        const copy: SearchResult = {
            ...searchResult,
            payload: {
                ...searchResult.payload,
                parsedContent: parsed,
                content: emptyTextUnitList
            }
        };
        dispatch(updateSearchResult(copy));
    }
};
