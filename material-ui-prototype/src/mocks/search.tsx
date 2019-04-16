import {SearchResult} from "../entities/SearchResult";
import AnnotatedElement from "../components/annotations/AnnotatedElement";
import * as React from "react";


const firstResult: SearchResult =
    {
        snippet: <span> <AnnotatedElement id='0' text='Ed Sheeran' details='A british singer'/> visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew ,</span>,
        url: 'https://www.borgenmagazine.com/ed-sheeran-visited-liberia/'
    }
;

const secondResult: SearchResult = {
    snippet: <span>President Donald Trump visited San Antonio for a closed-<AnnotatedElement id='2' text='door'
                                                                                             details='Hard to explain actually :O'/> fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ...</span>,
    url: 'https://www.mysanantonio.com/news/local/article/President-Trump-arrives-in-San-Antonio-for-13756986.php'
}

const thirdResult: SearchResult = {
    snippet: <span> The president of the Czech republic Milos Zeman visited a <AnnotatedElement id='3' text='porcelain'
                                                                                                details='Careful, it is fragile :)'/>   factory Thun 1794 within his two-day visit to Karlovy Vary region. The president met with ...</span>,
    url: 'https://www.thun.cz/en/article/238-visit-of-mr--president-milos-zeman.html'
}

const results = [firstResult, secondResult, thirdResult];
const randomResult = () => results[Math.floor(Math.random() * results.length) % results.length]

const resultArray: Array<SearchResult> = Array(50)
    .fill(null)
    .map(() => randomResult())

const search: ((query: string) => Promise<Array<SearchResult>>) = (query) => {
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

export default search;