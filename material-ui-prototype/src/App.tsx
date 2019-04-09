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

    const handleLogout = () => {
        setLoggedIn(false);
        setSnackbarState({
            isOpen: true,
            message: 'Logged out'
        });
    }

    const handleLogin = (username: string, password: string) => {
        setShowProgress(true);
        return new Promise((resolve) => {
            setTimeout(() => {
                setShowProgress(false);
                setSnackbarState({
                    isOpen: true,
                    message: 'Logged in'
                });
                setLoggedIn(true);
                resolve();
            }, 2000);
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
                handleLogout={handleLogout}/>
            {showProgress && <LinearProgress color="secondary"/>}

            <Switch>
                <Route path="/" exact component={() => <SearchBar startSearching={startSearching}/>}/>
                <Route path="/login" component={() => <Login isLoggedIn={isLoggedId} login={handleLogin}/>}/>
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
