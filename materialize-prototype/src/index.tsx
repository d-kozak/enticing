import React, {useRef, useState} from 'react';
import ReactDOM from 'react-dom';

import * as serviceWorker from './serviceWorker';

import 'materialize-css/dist/css/materialize.min.css';
// Import Materialize
import M from "materialize-css";

import './index.css';


interface SearchBarProps {
    query: string
    startSearch: () => void
    setQuery: (query: string) => void
    showLabel?: boolean
}

const SearchBar = ({query, startSearch, setQuery, showLabel = true}: SearchBarProps) => {

    const maxSize = 50;
    const minSize = 30;
    const colCount = query.length < minSize ? minSize : (query.length > maxSize ? maxSize : query.length)
    const rowCount = (query.length / maxSize) + 1

    console.log(`row,col [${rowCount},${colCount}]`)

    const textAreaEl = useRef<HTMLTextAreaElement>(null);

    setTimeout(() => {
        if (textAreaEl.current) {
            textAreaEl.current.focus();
        }
    }, 0);

    return <div className="input-field">
        <i className="material-icons prefix">search</i>
        <textarea id="textarea1" value={query} rows={rowCount} cols={colCount}
                  ref={textAreaEl}
                  onChange={e => setQuery(e.target.value.replace('\n', ''))}
                  onKeyUp={e => {
                      if (e.keyCode == 13) {
                          e.preventDefault();
                          startSearch();
                      }
                  }} className="materialize-textarea"/>
        {showLabel && <label htmlFor="textarea1">Search...</label>}
    </div>
};

const App = () => {
    M.AutoInit();

    const [isSearching, setIsSearching] = useState(false);
    const [query, setQuery] = useState('');
    const [queryProgress, setQueryProgress] = useState(0);

    const [results, setResults] = useState<Array<String>>([]);

    const startSearch = () => {
        setIsSearching(true);
        console.log(`Quering ${query}`);

        let shadow = 0;

        let intervalId: any;
        intervalId = setInterval(() => {
            const tmp = shadow;
            if (tmp == 100) {
                clearInterval(intervalId);
                setQueryProgress(0);
                setIsSearching(false);
                setResults(['a', 'b', 'c']);
                return;
            }
            const newProgress = (tmp + 10) % 101;
            shadow = newProgress;
            setQueryProgress(newProgress)
        }, 100);

    };

    return <React.Fragment>
        <nav className="nav-extended purple darken-3">
            <div className="nav-wrapper">
                {results.length != 0 ? <a href="" className="brand-logo">Corproc search</a> : ""}
                <ul id="nav-mobile" className="right">
                    <li><a href="">Login</a></li>
                </ul>
            </div>
        </nav>
        {isSearching && (<div className="progress">
            <div className="determinate" style={{width: `${queryProgress}%`}}></div>
        </div>)}
        <div className="content">
            {results.length != 0 &&
            <div className="SearchBarTop">
                <div className="container">
                    <SearchBar showLabel={false} query={query} startSearch={startSearch} setQuery={setQuery}/>
                </div>
            </div>}
            {results.length == 0 && (<div className="SearchBarMain container">
                <div className="row">
                    <div className="col s12">
                        <h1>Corproc search</h1>
                    </div>
                    <div className="col s12">
                        <SearchBar showLabel={false} query={query} startSearch={startSearch} setQuery={setQuery}/>
                    </div>
                </div>
            </div>)
            }
            {results.length != 0 &&
            <div className="container">
                <div className="search-results">
                    <p>Querying '{query}' took XXX seconds...</p>
                    <ul className="collection"> {results.map(((result, index) => <li key={index}
                                                                                     className="collection-item">{result}</li>))} </ul>
                    <ul className="pagination">
                        <li className="disabled"><a href="#!"><i className="material-icons">chevron_left</i></a></li>
                        <li className="active"><a href="#!">1</a></li>
                        <li className="waves-effect"><a href="#!">2</a></li>
                        <li className="waves-effect"><a href="#!">3</a></li>
                        <li className="waves-effect"><a href="#!"><i className="material-icons">chevron_right</i></a>
                        </li>
                    </ul>
                </div>
            </div>}
        </div>
    </React.Fragment>
}

ReactDOM.render(<App/>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
