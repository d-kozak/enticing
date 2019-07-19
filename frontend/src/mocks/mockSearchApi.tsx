import * as React from "react";
import {Dispatch} from "redux";
import * as H from "history";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {newSearchResultsAction} from "../actions/SearchResultActions";
import {openSnackBar} from "../actions/SnackBarActions";
import {SearchQuery} from "../entities/SearchQuery";
import {Snippet} from "../entities/Snippet";
import {Interval, NewAnnotatedText, QueryMatch, Word} from "../components/annotations/new/NewAnnotatedText";
import {CorpusFormat} from "../entities/CorpusFormat";
import {DonaldTrump, EdSheeran, KarlovyVary} from "./mockAnnotations";
import {getProcessedSnippets} from "./realResponse";


export const mockCorpusFormat: CorpusFormat = {
    corpusName: "mockCorpus",
    indexes: {
        token: "token",
        lemma: "lemma"
    },
    entities: {
        person: {
            description: "",
            attributes: {
                name: 'name',
                url: 'url',
                gender: 'gender',
                birthplace: 'birthplace',
                birthdate: 'birthdate',
                image: 'image',
                nertag: 'person'
            }
        },
        location: {
            description: "",
            attributes: {
                name: 'name',
                url: 'url',
                country: 'country',
                image: 'image',
                nertag: 'nertag'
            }
        }
    }
}

export const firstResult: Snippet = {
    id: "first",
    host: "server1",
    collection: "col1",
    documentId: 1,
    documentTitle: 'Ed on the road',
    location: 0,
    size: 42,
    payload: {
        content: new NewAnnotatedText([new QueryMatch(new Interval(0, 1), [EdSheeran, new Word(["visited", "visit"])]), new Word(["Liberia"]), new Word(["and"]), new Word(["meets"]), new Word(["JD,"]), new Word(["a"]), new Word(["homeless"]), new Word(["Liberian"]), new Word(["14-year-old"]), new Word(["boy."]), new Word(["After"]), new Word(["Sheeran"]), new Word(["saw"]), new Word(["an"]), new Word(["older"]), new Word(["man"]), new Word(["hitting"]), new Word(["JD"]), new Word(["in"]), new Word(["public,"]), new Word(["he"]), new Word(["knew"])])
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
        content: new NewAnnotatedText([new Word(["President"]), DonaldTrump, new QueryMatch(new Interval(0, 1), [new Word(["visited"])]), new Word(["San"]), new Word(["Antonio"]), new Word(["for"]), new Word(["a"]), new Word(["closed-door"]), new Word(["fundraiser"]), new Word(["at"]), new Word(["The"]), new Word(["Argyle,"]), new Word(["the"]), new Word(["exclusive"]), new Word(["dinner"]), new Word(["club"]), new Word(["in"]), new Word(["Alamo"]), new Word(["Heights."]), new Word(["Air"]), new Word(["Force"]), new Word(["..."])])
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
        content: new NewAnnotatedText([new Word(["The"]), new Word(["president"]), new Word(["of"]), new Word(["the"]), new Word(["Czech"]), new Word(["republic"]), new Word(["Milos"]), new Word(["Zeman"]), new QueryMatch(new Interval(0, 1), [new Word(["visited"])]), new Word(["a"]), new Word(["porcelain"]), new Word(["factory"]), new Word(["Thun"]), new Word(["1794"]), new Word(["within"]), new Word(["his"]), new Word(["two-day"]), new Word(["visit"]), new Word(["to"]), KarlovyVary, new Word(["region."]), new Word(["The"]), new Word(["president"]), new Word(["met"]), new Word(["with"]), new Word(["..."])])
    },
    url: 'https://www.thun.cz/en/article/238-visit-of-mr--president-milos-zeman.html',
    canExtend: true
};

const results = [firstResult, secondResult, thirdResult];
const randomResult = () => results[Math.floor(Math.random() * results.length) % results.length]

const resultArray: Array<Snippet> = Array(50)
    .fill(null)
    .map((_, index) => ({...randomResult(), id: "id-" + index, canExtend: Math.random() > 0.3}))


const mockExecuteQuery: ((query: string) => Promise<Array<Snippet>>) = (query) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            switch (query) {
                case 'nertag:person (visited|entered)':
                    resolve(getProcessedSnippets());
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
            dispatch(newSearchResultsAction(results, mockCorpusFormat));
            if (history) {
                history.push(`/search?query=${encodedQuery}`);
            }
        }).catch(error => {
        dispatch(openSnackBar(`Error ${error}`));
    }).finally(() => {
        dispatch(hideProgressBarAction());
    });

};