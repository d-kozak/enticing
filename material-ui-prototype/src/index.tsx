import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import * as serviceWorker from './serviceWorker';
// register the custom mode
// note: since the constant MG4J_EQL is used inside the SearchBar component, the
// language module would be registered anyways, but I consider that as a side effect
// and therefore I am keeping this import here to make it more explicit
import './codemirror/LanguageMode';

ReactDOM.render(<App/>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
