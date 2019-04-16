import {SearchResult} from "../entities/SearchResult";
import * as React from "react";


const firstResult: SearchResult =
    {
        snippet: "Ed Sheeran visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew",
        url: 'https://www.borgenmagazine.com/ed-sheeran-visited-liberia/'
    }
;

const secondResult: SearchResult = {
    snippet: "President Donald Trump visited San Antonio for a closed-door fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ...",
    url: 'https://www.mysanantonio.com/news/local/article/President-Trump-arrives-in-San-Antonio-for-13756986.php'
}

const thirdResult: SearchResult = {
    snippet: "The president of the Czech republic Milos Zeman visited a porcelain factory Thun 1794 within his two-day visit to Karlovy Vary region. The president met with ...",
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