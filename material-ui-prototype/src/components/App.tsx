import React, {useState} from 'react';
import MenuAppBar from "./appbar/CorprocAppBar";
import {CssBaseline} from "@material-ui/core";
import CorprocSnackBar from "./notifiers/CorprocSnackbar";
import SearchBar from "./searchbar/CenteredSearchBar";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import LoginComponent from "./maincontent/Login";
import UnknownRoute from "./maincontent/UnknownRoute";


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
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                if (username != 'dkozak' && password != 'dkozak') {
                    setSnackbarState({
                        isOpen: true,
                        message: 'Logged in'
                    });
                    setLoggedIn(true);
                    resolve();
                } else if (username == 'dkozak' && password != 'dkozak') {
                    reject({login: 'Unknown login'});
                } else if (username != 'dkozak' && password == 'dkozak') {
                    reject({password: 'Invalid password'})
                } else {
                    reject({login: 'Unknown login'});
                }
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


    return <React.Fragment>
        <CssBaseline/>
        <Router>
            <MenuAppBar
                isLoggedIn={isLoggedId}
                handleLogout={handleLogout}/>
            {showProgress && <LinearProgress color="secondary"/>}

            <Switch>
                <Route path="/" exact render={() => <SearchBar startSearching={startSearching}/>}/>
                <Route path="/login" render={() => <LoginComponent isLoggedIn={isLoggedId} login={handleLogin}/>}/>
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
