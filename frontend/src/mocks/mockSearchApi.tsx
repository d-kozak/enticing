import * as React from "react";
import {DonaldTrump, EdSheeran, KarlovyVary} from "./mockAnnotations";
import {Dispatch} from "redux";
import * as H from "history";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {newSearchResultsAction} from "../actions/SearchResultActions";
import {openSnackBar} from "../actions/SnackBarActions";
import {SearchQuery} from "../entities/SearchQuery";
import {isSnippet, Snippet} from "../entities/Snippet";
import {MatchedRegion} from "../entities/Annotation";
import {realSnippet} from "./realSnippet";
import {searchResultWithEntities} from "./searchResultWithEntities";
import {cloneDeep} from "lodash";
import {transformAnnotatedText} from "../actions/QueryActions";


export const firstResult: Snippet = {
    id: "first",
    host: "server1",
    collection: "col1",
    documentId: 1,
    documentTitle: 'Ed on the road',
    location: 0,
    size: 42,
    payload: {
        content: {
            text: "Ed Sheeran visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew",
            annotations: {
                "ed": EdSheeran
            },
            positions: [{
                annotationId: "ed", match: new MatchedRegion(0, 10), subAnnotations: []
            }],
            queryMapping: [
                {
                    textIndex: new MatchedRegion(0, 10),
                    queryIndex: new MatchedRegion(0, 13)
                }]
        }
    },
    url: 'https://www.borgenmagazine.com/ed-sheeran-visited-liberia/',
    canExtend: true
};

export const secondResult: Snippet = {
    id: "second",
    host: "server2",
    collection: "col2",
    documentId: 2,
    documentTitle: "just donald",
    location: 0,
    size: 42,
    payload: {
        content: {
            text: "President Donald Trump visited San Antonio for a closed-door fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ...",
            annotations: {
                "donald": DonaldTrump
            },
            positions: [{
                annotationId: "donald", match: new MatchedRegion(10, 12), subAnnotations: []
            }],
            queryMapping: [
                {
                    textIndex: new MatchedRegion(0, 16),
                    queryIndex: new MatchedRegion(0, 13)
                }]
        }
    },
    url: 'https://www.mysanantonio.com/news/local/article/President-Trump-arrives-in-San-Antonio-for-13756986.php',
    canExtend: true
};

export const thirdResult: Snippet = {
    id: "third",
    host: "server3",
    collection: "col3",
    documentId: 3,
    documentTitle: "Milos",
    location: 0,
    size: 42,
    payload: {
        content: {
            text: "The president of the Czech republic Milos Zeman visited a porcelain factory Thun 1794 within his two-day visit to Karlovy Vary region. The president met with ...",
            annotations: {
                "kv": KarlovyVary
            },
            positions: [{
                annotationId: "kv", match: new MatchedRegion(114, 13), subAnnotations: []
            }],
            queryMapping: [
                {
                    textIndex: new MatchedRegion(117, 23),
                    queryIndex: new MatchedRegion(0, 13)
                }]
        }
    },
    url: 'https://www.thun.cz/en/article/238-visit-of-mr--president-milos-zeman.html',
    canExtend: true
};

const results = [firstResult, secondResult, thirdResult, realSnippet];
const randomResult = () => results[Math.floor(Math.random() * results.length) % results.length]

const resultArray: Array<Snippet> = Array(50)
    .fill(null)
    .map((_, index) => ({...randomResult(), id: "id-" + index, canExtend: Math.random() > 0.3}))


const mockExecuteQuery: ((query: string) => Promise<Array<Snippet>>) = (query) => {
    // @ts-ignore
    const snippetsWithEntities: Array<Snippet> = cloneDeep(searchResultWithEntities).snippets;

    for (let snippet of snippetsWithEntities) {
        if (!isSnippet(snippet)) continue
        transformAnnotatedText(snippet.payload.content)
    }

    return new Promise((resolve, reject) => {
        setTimeout(() => {
            switch (query) {
                case 'nertag:person (visited|entered)':
                    resolve(snippetsWithEntities);
                    break;
                case 'fail':
                    reject("booom!");
                    break;
                default:
                    resolve([]);
            }
        }, 2000);
    })
};

export const mockSearch = (query: SearchQuery, dispatch: Dispatch, history?: H.History) => {

    dispatch(showProgressBarAction())
    mockExecuteQuery(query)
        .then(results => {
            const encodedQuery = encodeURI(query)
            dispatch(newSearchResultsAction(results));
            if (history) {
                history.push(`/search?query=${encodedQuery}`);
            }
        }).catch(error => {
        dispatch(openSnackBar(`Error ${error}`));
    }).finally(() => {
        dispatch(hideProgressBarAction());
    });

};