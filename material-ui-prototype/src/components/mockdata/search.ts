import {SearchResult} from "../../entities/SearchResult";


const killedData: SearchResult =
    {
        snippet: 'The singer\'s funeral procession descended into chaos with one person killed and three injured',
        fullText: 'Thousands of mourners lined the streets as the singer\'s hearse made its way through Los Angeles. However, it quickly descended into chaos when a vehicle drove past and shot four people.\n' +
            '\n' +
            'Officers confirmed three men and a woman, aged between 30 and 50, were hit by bullets fired form the grey vehicle, which is now being hunted down by authorities.\n' +
            '\n' +
            'The tragic news was announced by police chief Michel Moore who shared a statement to his Twitter page, calling for an end to gun violence.',
        largerText: 'Thousands of mourners lined the streets as the singer\'s hearse made its way through Los Angeles. However, it quickly descended into chaos when a vehicle drove past and shot four people.\n' +
            '\n' +
            'Officers confirmed three men and a woman, aged between 30 and 50, were hit by bullets fired form the grey vehicle, which is now being hunted down by authorities.\n' +
            '\n',
        url: 'http://www.ladbible.com/entertainment/news-one-person-killed-and-three-injured-at-nipsey-hussles-funeral-20190412'
    }
;

const search: ((query: string) => Promise<Array<SearchResult>>) = (query) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            switch (query) {
                case 'nertag:person killed':
                    resolve(Array(50).fill(killedData));
                    break;
                case 'fail':
                    reject("booom!");
                    break;
                default:
                    resolve([]);
            }
        }, 2000)
    })
};

export default search;