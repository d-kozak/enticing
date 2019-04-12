import {SearchResult} from "../../entities/SearchResult";


const firstResult: SearchResult =
    {
        snippet: 'Ed Sheeran visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew ',
        largerText: 'British singer-songwriter Ed Sheeran visited West Point in Monrovia, Liberia for Red Nose Day 2017. West Point is a large and dangerous slum in Liberia’s capital city. Sheeran’s Liberia visit was made possible by Red Nose Day, a fundraising campaign operated by the nonprofit organization Comic Relief. Ed Sheeran visited Liberia to work with the charity Street Child and help children living in poverty.',
        fullText: '',
        url: 'https://www.borgenmagazine.com/ed-sheeran-visited-liberia/'
    }
;

const secondResult: SearchResult = {
    snippet: 'President Donald Trump visited San Antonio for a closed-door fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ...',
    largerText: 'Air Force One landed at the San Antonio International Airport in the late morning on Wednesday, where the president was greeted by numerous supporters as he walked off the plane. Trump then rode in a limousine to The Argyle behind a motorcade of police motorcycles, cruisers and ambulance trucks.',
    fullText: '',
    url: 'https://www.mysanantonio.com/news/local/article/President-Trump-arrives-in-San-Antonio-for-13756986.php'
}

const thirdResult: SearchResult = {
    snippet: 'The president of the Czech republic Milos Zeman visited a porcelain factory Thun 1794 within his two-day visit to Karlovy Vary region. The president met with ...',
    largerText: 'The president of the Czech republic Milos Zeman visited a porcelain factory Thun 1794 within his two-day visit to Karlovy Vary region.' +
        'The president met with representatives of the management of the porcelain in the showroom and then visited also operation where he personally tried to decorate the plate. Plate was burned and then immediately sent as a memorial to his visit to Prague Castle.' +
        'The president also said a few words to porcelain factory workers, who were waiting for him in a production hall in the factory. At the end of his visit he received a gift of tea sets of new retro shape Louise.',
    fullText: '',
    url: 'https://www.thun.cz/en/article/238-visit-of-mr--president-milos-zeman.html'
}

const results = [firstResult, secondResult, thirdResult];
const randomResult = () => results[Math.floor(Math.random() * results.length) % results.length]

const resultArray: Array<SearchResult> = Array(50)
    .fill(null)
    .map(() => randomResult())
    .map((result, index) => ({...result, snippet: result.snippet + index}))

const search: ((query: string) => Promise<Array<SearchResult>>) = (query) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            switch (query) {
                case 'nertag:person visited':
                    resolve(resultArray);
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