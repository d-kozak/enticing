import {SearchResult} from "../../entities/SearchResult";


const search: ((query: string) => Promise<Array<SearchResult>>) = (query) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            switch (query) {
                case 'john':
                    resolve([
                        {snippet: 'John went there...'},
                        {snippet: 'John did that...'},
                        {snippet: 'John stole what...'}
                    ]);
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