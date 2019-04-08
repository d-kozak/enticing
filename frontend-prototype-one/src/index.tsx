import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';


const Search = () => <div>
    <h1>Corproc search</h1>
    <input type="text" placeholder="What do you want to know?"/>
</div>

ReactDOM.render(<Search/>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
