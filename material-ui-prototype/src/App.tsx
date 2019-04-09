import React, {useState} from 'react';
import './App.css';
import MenuAppBar from "./CorprocAppBar";
import {CssBaseline} from "@material-ui/core";
import CorprocSnackBar from "./CorprocSnackbar";
import SearchBar from "./CenteredSearchBar";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Login from "./Login";
import Unknown from "./Unknown";


const App = () => {
    const [isLoggedId, setLoggedIn] = useState(false);

    const [snackbarState, setSnackbarState] = useState({
        isOpen: false,
        message: ''
    });

    const [showProgress, setShowProgress] = useState(false);

    const handleLogin = (isLoggedIn: boolean) => {
        setLoggedIn(isLoggedIn);
        setSnackbarState({
            isOpen: true,
            message: isLoggedIn ? 'Logged in' : 'Logged out'
        });
    }

    const startSearching = (query: string) => {
        setSnackbarState({
            isOpen: true,
            message: `Quering '${query}'`
        });
        setShowProgress(true);
        setTimeout(() => {
            setShowProgress(false);
        }, 3000);
    };

    return <div>
        <CssBaseline/>
        <Router>
            <MenuAppBar
                isLoggedIn={isLoggedId}
                setLoggedIn={handleLogin}/>
            {showProgress && <LinearProgress color="secondary"/>}

            <Switch>
                <Route path="/" exact component={() => <SearchBar startSearching={startSearching}/>}/>
                <Route path="/login" component={Login}/>
                <Route component={Unknown}/>
            </Switch>
        </Router>
        <CorprocSnackBar isOpen={snackbarState.isOpen}
                         setClosed={() => setSnackbarState({...snackbarState, isOpen: false})}
                         message={snackbarState.message}
        />
    </div>
};


export default App;
