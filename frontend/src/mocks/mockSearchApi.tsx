import {SearchResult} from "../entities/SearchResult";
import * as React from "react";
import {DonaldTrump, EdSheeran, KarlovyVary} from "./mockAnnotations";
import {Dispatch} from "redux";
import * as H from "history";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {newSearchResultsAction} from "../actions/SearchResultActions";
import {openSnackBar} from "../actions/SnackBarActions";
import {SearchQuery} from "../entities/SearchQuery";


export const firstResult: SearchResult = {
    id: 0,
    snippet: {
        text: "Ed Sheeran visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew",
        annotations: new Map([[1, EdSheeran]]),
        positions: [{annotationId: 1, from: 0, to: 10}]
    },
    url: 'https://www.borgenmagazine.com/ed-sheeran-visited-liberia/',
    canExtend: true
};

const secondResult: SearchResult = {
    id: 1,
    snippet: {
        text: "President Donald Trump visited San Antonio for a closed-door fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ...",
        annotations: new Map([[2, DonaldTrump]]),
        positions: [{annotationId: 2, from: 10, to: 22}]
    },
    url: 'https://www.mysanantonio.com/news/local/article/President-Trump-arrives-in-San-Antonio-for-13756986.php',
    canExtend: true
};

const thirdResult: SearchResult = {
    id: 2,
    snippet: {
        text: "The president of the Czech republic Milos Zeman visited a porcelain factory Thun 1794 within his two-day visit to Karlovy Vary region. The president met with ...",
        annotations: new Map([[3, KarlovyVary]]),
        positions: [{annotationId: 3, from: 114, to: 127}]
    },
    url: 'https://www.thun.cz/en/article/238-visit-of-mr--president-milos-zeman.html',
    canExtend: true
};

const results = [firstResult, secondResult, thirdResult];
const randomResult = () => results[Math.floor(Math.random() * results.length) % results.length]

const resultArray: Array<SearchResult> = Array(50)
    .fill(null)
    .map((_, index) => ({...randomResult(), id: index, canExtend: Math.random() > 0.3}))

const mockExecuteQuery: ((query: string) => Promise<Array<SearchResult>>) = (query) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            switch (query) {
                case 'nertag:person (visited|entered)':
                    resolve(resultArray);
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