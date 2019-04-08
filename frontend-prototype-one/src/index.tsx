import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';


const SearchBar = () => {
    const [query, setQuery] = useState('');

    const onQueryChange = (newQuery: string) => {
        setQuery(newQuery);
    };

    const inputSize = query.length > 20 ? query.length : 20;

    return <div>
        <input size={inputSize} type="text" placeholder="Search..." value={query}
               onChange={e => onQueryChange(e.target.value)}/>
    </div>
}

const Logo = () => <h1>Corproc search</h1>

const Header = () => <div className="Header">
    <h1>Header</h1>
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
