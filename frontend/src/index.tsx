import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from "react-redux";
import store from "./store";
import App from "./components/App";
import * as serviceWorker from './serviceWorker';
// register the custom mode
// note: since the constants MG4J_EQL and CONSTRAINTS are used inside the SearchBar component, the
// language modules would be registered anyways, but I consider that as a side effect
// and therefore I am keeping these imports here to make it more explicit
import './codemirror/mg4jmode';
import './codemirror/constraintsMode';


const Root = <Provider store={store}>
    <App/>
</Provider>;

ReactDOM.render(Root, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
