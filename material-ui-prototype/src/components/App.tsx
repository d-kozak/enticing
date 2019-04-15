import React, {useState} from 'react';
import CorprocAppBar from "../containers/appbar/CorprocAppBar";
import {CssBaseline} from "@material-ui/core";
import CorprocSnackBar from "./notifiers/CorprocSnackbar";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import {BrowserRouter as Router, Redirect, Route, Switch} from 'react-router-dom';
import Login from "../containers/maincontent/Login";
import UnknownRoute from "./maincontent/UnknownRoute";
import SignUp from "./maincontent/SignUp";
import {SearchResult} from "../entities/SearchResult";
import search from "./mockdata/search";
import Search from "./maincontent/SearchResultPage";
import MainPage from "./maincontent/MainPage";
import Settings from "./maincontent/Settings";
import UserManagement from "./maincontent/UserManagement";
import AdminRoute from "./routes/AdminRoute";


const App = () => {
    const [query, setQuery] = useState('nertag:person (visited|entered)');

    const [isLoggedId, setLoggedIn] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);

    const [searchResults, setSearchResults] = useState<Array<SearchResult> | null>(null);

    const [snackbarState, setSnackbarState] = useState({
        isOpen: false,
        message: ''
    });

    const [progressBarVisible, setShowProgressBar] = useState(false);

    const handleLogout = () => {
        setLoggedIn(false);
        setIsAdmin(false);
        setSnackbarState({
            isOpen: true,
            message: 'Logged out'
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
        setShouldRedirectToSearchPage(true);
        setShowProgressBar(true);
        search(query)
            .then(results => {
                setSearchResults(results);
            }).catch(error => {
            setSnackbarState({
                isOpen: true,
                message: `Error ${error}`
            });
        }).finally(() => {
            setShowProgressBar(false);
        });
    };

    const showSnackBarMessage = (message: string) => {
        setSnackbarState({
            isOpen: true,
            message
        })
    };

    const [shouldRedirectToSearchPage, setShouldRedirectToSearchPage] = useState(true);

    return <React.Fragment>
        <CssBaseline/>
        <Router>
            <CorprocAppBar/>
            {progressBarVisible && <LinearProgress color="secondary"/>}

            <Switch>
                <Route path="/" exact
                       render={() => <React.Fragment>
                           {shouldRedirectToSearchPage && searchResults && <Redirect to="/search" push/>}
                           <MainPage query={query} setQuery={setQuery} startSearching={startSearching}/>
                       </React.Fragment>}/>
                <Route path="/search"
                       render={() => {
                           setShouldRedirectToSearchPage(false);
                           return <Search query={query} setQuery={setQuery} startSearching={startSearching}
                                          searchResults={searchResults}/>;
                       }}/>
                <Route path="/login" render={() => <Login/>}/>
                <Route path="/signup" render={() => <SignUp isLoggedIn={isLoggedId} signUp={handleSignUp}/>}/>
                <Route path="/settings" render={() => <Settings isLoggedIn={isLoggedId}/>}/>
                <AdminRoute path="/users" isAdmin={isAdmin} showSnackBarMessage={showSnackBarMessage}
                            render={() => <UserManagement/>}/>
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
