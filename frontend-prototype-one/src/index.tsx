import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';


const SearchBar = () => {
    const [query, setQuery] = useState('');

    const onQueryChange = (newQuery: string) => {
        setQuery(newQuery);
    };


    const maxSize = 50;
    const minSize = 30;
    const colCount = query.length < minSize ? minSize : (query.length > maxSize ? maxSize : query.length)
    const rowCount = (query.length / maxSize) + 1

    return <div>
        <textarea rows={rowCount} cols={colCount} placeholder="Search..." value={query}
                  onChange={e => onQueryChange(e.target.value)}/>
    </div>
}
const Logo = () => <h1>Corproc search</h1>

const Header = () => <div className="Header">
    <button>Login</button>
</div>

const App = () => {
    return <div className="App">
        <Header/>
        <div className="search-bar-main">
            <Logo/>
            <SearchBar/>
        </div>
    </div>
};

ReactDOM.render(<App/>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
