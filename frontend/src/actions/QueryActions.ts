import {ThunkResult} from "./RootActions";
import * as H from "history";
import {SearchQuery} from "../entities/SearchQuery";
import {API_BASE_PATH} from "../globals";
import axios from "axios";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {isSearchResult} from "../entities/SearchResult";
import {parseNewAnnotatedText} from "../components/annotations/NewAnnotatedText";
import {SearchSettings} from "../entities/SearchSettings";
import {openSnackbar} from "../reducers/SnackBarReducer";
import {newSearchResults} from "../reducers/SearchResultReducer";

export const startSearchingAction = (query: SearchQuery, searchSettings: SearchSettings, history?: H.History): ThunkResult<void> => (dispatch) => {
    const encodedQuery = encodeURI(query);
    if (!searchSettings.corpusFormat) {
        console.log('No corpus format is loaded, cannot perform search');
        return
    }
    dispatch(showProgressbar());
    axios.get(`${API_BASE_PATH}/query`, {
        params: {
            query: encodedQuery,
            settings: searchSettings.id
        },
        withCredentials: true
    }).then(response => {
        if (!isSearchResult(response.data)) {
            throw `Invalid search result ${JSON.stringify(response.data, null, 2)}`;
        }
        for (let error in response.data.errors) {
            dispatch(openSnackbar(`Error from ${error}: ${response.data.errors[error]}`))
        }
        for (let snippet of response.data.snippets) {
            snippet.id = `${snippet.host}:${snippet.collection}:${snippet.documentId}`
            const parsed = parseNewAnnotatedText(snippet.payload.content);
            if (parsed !== null) {
                snippet.payload.content = parsed
            } else {
                console.error("could not parse snippet " + JSON.stringify(snippet))
            }
        }

        dispatch(newSearchResults({snippets: response.data.snippets, corpusFormat: searchSettings.corpusFormat!}));
        dispatch(hideProgressbar());
        if (history) {
            history.push(`/search?query=${encodedQuery}`);
        }
    }).catch((error) => {
        console.error(error);
        dispatch(openSnackbar(`Could not load search results`));
        dispatch(hideProgressbar());
    })
};

