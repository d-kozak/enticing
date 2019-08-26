import {ThunkResult} from "./RootActions";
import * as H from "history";
import {
    ExactEntityDefinition,
    ExactFormatDefinition,
    ExactIndexDefinition,
    IndexDefinition,
    Predefined,
    SearchQuery,
    TextMetadata
} from "../entities/SearchQuery";
import {API_BASE_PATH} from "../globals";
import axios from "axios";
import {hideProgressbar, showProgressbar} from "../reducers/ProgressBarReducer";
import {isResultList} from "../entities/ResultList";
import {parseNewAnnotatedText} from "../components/annotations/TextUnitList";
import {SearchSettings} from "../entities/SearchSettings";
import {openSnackbar} from "../reducers/SnackBarReducer";
import {newSearchResults} from "../reducers/SearchResultReducer";
import {User} from "../entities/User";
import {CorpusFormat, EntityInfo, IndexInfo, String2StringObjectMap} from "../entities/CorpusFormat";
import {SelectedMetadata} from "../entities/SelectedMetadata";


export const startSearchingAction = (query: string, user: User, searchSettings: SearchSettings, history?: H.History): ThunkResult<void> => (dispatch) => {
    if (!searchSettings.corpusFormat) {
        console.log('No corpus format is loaded, cannot perform search');
        return
    }
    const metadata = createMetadataRequest(searchSettings.corpusFormat, user.selectedMetadata[searchSettings.id]);
    const filteredCorpusFormat = filterCorpusFormat(searchSettings.corpusFormat, user.selectedMetadata[searchSettings.id]);
    const searchQuery: SearchQuery = {
        query,
        metadata,
        defaultIndex: "token",
        resultFormat: "SNIPPET",
        textFormat: "TEXT_UNIT_LIST",
        snippetCount: user.userSettings.resultsPerPage
    };
    dispatch(showProgressbar());
    axios.post(`${API_BASE_PATH}/query?settings=${searchSettings.id}`, searchQuery, {
        withCredentials: true
    }).then(response => {
        if (!isResultList(response.data)) {
            throw `Invalid search result ${JSON.stringify(response.data, null, 2)}`;
        }
        for (let error in response.data.errors) {
            dispatch(openSnackbar(`Error from ${error}: ${response.data.errors[error]}`))
        }
        for (let i in response.data.searchResults) {
            const snippet = response.data.searchResults[i];
            snippet.id = `${snippet.host}:${snippet.collection}:${snippet.documentId}:${i}`;
            const parsed = parseNewAnnotatedText(snippet.payload.content);
            if (parsed !== null) {
                snippet.payload.content = parsed
            } else {
                console.error("could not parse snippet " + JSON.stringify(snippet))
            }
        }

        dispatch(newSearchResults({snippets: response.data.searchResults, corpusFormat: filteredCorpusFormat}));
        dispatch(hideProgressbar());
        if (history) {
            history.push(`/search?query=${encodeURI(query)}`);
        }
    }).catch((error) => {
        console.error(error);
        dispatch(openSnackbar(`Could not load search results`));
        dispatch(hideProgressbar());
    })
};


function filterCorpusFormat(corpusFormat: CorpusFormat, selectedMetadata: SelectedMetadata | undefined): CorpusFormat {
    // @Warning should we clone the object where or are we sure that no one dares to modify it?
    if (!selectedMetadata) return corpusFormat;
    const indexes: IndexInfo = {};
    const entities: { [clazz: string]: EntityInfo } = {};

    for (let index of selectedMetadata.indexes) {
        if (!corpusFormat.indexes[index]) {
            console.warn(`index ${index} is not part of selected corpus format`);
            continue;
        }
        indexes[index] = corpusFormat.indexes[index];
    }

    for (let entityName of Object.keys(selectedMetadata.entities)) {
        const entity = selectedMetadata.entities[entityName];
        const entityInfo = corpusFormat.entities[entityName];
        if (!entityInfo) {
            console.warn(`entity ${entityName} is not part of selected corpus format`);
            continue;
        }
        const wantedAttributes: String2StringObjectMap = {};
        for (let attribute of entity.attributes) {
            if (entityInfo.attributes[attribute]) {
                wantedAttributes[attribute] = entityInfo.attributes[attribute];
            } else {
                console.warn(`attribute ${attribute} is not part of selected entity ${entityName}`);
            }
        }
        entities[entityName] = {
            description: entityInfo.description,
            attributes: wantedAttributes
        };
    }

    return {
        corpusName: corpusFormat.corpusName,
        indexes,
        entities
    };
}


function createMetadataRequest(corpusFormat: CorpusFormat, selectedMetadata: SelectedMetadata | undefined): TextMetadata {
    if (!selectedMetadata) return new Predefined("all");
    const wantedIndexes = [] as Array<string>;
    for (let index of selectedMetadata.indexes) {
        if (corpusFormat.indexes[index]) {
            wantedIndexes.push(index);
        } else {
            console.warn(`index ${index} is not part of selected corpus format`)
        }
    }
    const indexDef: IndexDefinition = wantedIndexes.length == Object.keys(corpusFormat.indexes).length ? new Predefined("all") : new ExactIndexDefinition(wantedIndexes);

    const wantedEntities: { [key: string]: IndexDefinition } = {};
    for (let entityName of Object.keys(selectedMetadata.entities)) {
        const entity = selectedMetadata.entities[entityName];
        const entityInfo = corpusFormat.entities[entityName];
        if (!entityInfo) {
            console.warn(`entity ${entityName} is not part of selected corpus format`);
            continue;
        }
        const wantedAttributes = [] as Array<string>;
        for (let attribute of entity.attributes) {
            if (entityInfo.attributes[attribute]) {
                wantedAttributes.push(attribute);
            } else {
                console.warn(`attribute ${attribute} is not part of selected entity ${entityName}`);
            }
        }
        wantedEntities[entityName] = wantedAttributes.length == Object.keys(entityInfo.attributes).length ? new Predefined("all") : new ExactIndexDefinition(wantedAttributes);
    }
    return new ExactFormatDefinition(indexDef, new ExactEntityDefinition(wantedEntities));
}