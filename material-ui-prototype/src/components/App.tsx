import React, {useState} from 'react';
import MenuAppBar from "./appbar/CorprocAppBar";
import {CssBaseline} from "@material-ui/core";
import CorprocSnackBar from "./notifiers/CorprocSnackbar";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Login from "./maincontent/Login";
import UnknownRoute from "./maincontent/UnknownRoute";
import SignUp from "./maincontent/SignUp";
import {SearchResult} from "../entities/SearchResult";
import HomeContent from "./maincontent/HomeContent";
import search from "./mockdata/search";


const App = () => {
    const [isLoggedId, setLoggedIn] = useState(false);

    const [searchResults, setSearchResults] = useState<Array<SearchResult> | null>(null);

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

    const handleLogin = (login: string, password: string) => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                if (login != 'dkozak' && password != 'dkozak') {
                    setSnackbarState({
                        isOpen: true,
                        message: 'Logged in'
                    });
                    setLoggedIn(true);
                    resolve();
                } else if (login == 'dkozak' && password != 'dkozak') {
                    reject({login: 'Unknown login'});
                } else if (login != 'dkozak' && password == 'dkozak') {
                    reject({password: 'Invalid password'})
                } else {
                    reject({login: 'Unknown login'});
                }
            }, 2000);
        });
    }

    const handleSignUp = (login: string, password: string) => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                if (login != 'dkozak') {
                    setSnackbarState({
                        isOpen: true,
                        message: 'Signed up successfully'
                    });
                    setLoggedIn(true);
                    resolve();
                } else {
                    reject({
                        login: 'This login is already taken'
                    });
                }
            }, 2000);
        });
    };

    const startSearching = (query: string) => {
        setShowProgress(true);
        search(query)
            .then(results => {
                setSearchResults(results);
            }).catch(error => {
            setSnackbarState({
                isOpen: true,
                message: `Error ${error}`
            });
        }).finally(() => {
            setShowProgress(false);
        });
    };


    return <React.Fragment>
        <CssBaseline/>
        <Router>
            <MenuAppBar
                isLoggedIn={isLoggedId}
                handleLogout={handleLogout}/>
            {showProgress && <LinearProgress color="secondary"/>}

            <Switch>
                <Route path="/" exact
                       render={() => <HomeContent startSearching={startSearching} searchResults={searchResults}/>}/>
                <Route path="/login" render={() => <Login isLoggedIn={isLoggedId} login={handleLogin}/>}/>
                <Route path="/signup" render={() => <SignUp isLoggedIn={isLoggedId} signUp={handleSignUp}/>}/>
                <Route component={UnknownRoute}/>
            </Switch>
        </Router>
        <CorprocSnackBar isOpen={snackbarState.isOpen}
                         setClosed={() => setSnackbarState({...snackbarState, isOpen: false})}
                         message={snackbarState.message}
        />
    </React.Fragment>
};


export default App;
