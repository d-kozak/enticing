import React, {useState} from 'react';
import CorprocAppBar from "../containers/appbar/CorprocAppBar";
import {CssBaseline} from "@material-ui/core";
import CorprocSnackBar from "../containers/snackbar/CorprocSnackbar";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import {BrowserRouter as Router, Redirect, Route, Switch} from 'react-router-dom';
import Login from "../containers/maincontent/Login";
import UnknownRoute from "./maincontent/UnknownRoute";
import SignUp from "./maincontent/SignUp";
import {SearchResult} from "../entities/SearchResult";
import Search from "./maincontent/SearchResultPage";
import MainPage from "./maincontent/MainPage";
import Settings from "./maincontent/Settings";
import UserManagement from "./maincontent/UserManagement";
import AdminRoute from "./routes/AdminRoute";
import {openSnackBar} from "../actions/SnackBarActions";
import {connect} from "react-redux";
import {AppState} from "../AppState";

interface AppProps {
    showSnackBarMessage: (message: string) => void
    isAdmin: boolean
}

const App = ({showSnackBarMessage, isAdmin}: AppProps) => {
    const [query, setQuery] = useState('nertag:person (visited|entered)');

    const [searchResults, setSearchResults] = useState<Array<SearchResult> | null>(null);

    const [progressBarVisible, setShowProgressBar] = useState(false);

    const startSearching = (query: string) => {
        // setShouldRedirectToSearchPage(true);
        // setShowProgressBar(true);
        // search(query)
        //     .then(results => {
        //         setSearchResults(results);
        //     }).catch(error => {
        //     setSnackbarState({
        //         isOpen: true,
        //         message: `Error ${error}`
        //     });
        // }).finally(() => {
        //     setShowProgressBar(false);
        // });
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
                <Route path="/signup" render={() => <SignUp/>}/>
                <Route path="/settings" render={() => <Settings/>}/>
                <AdminRoute path="/users" isAdmin={isAdmin} showSnackBarMessage={showSnackBarMessage}
                            render={() => <UserManagement/>}/>
                <Route component={UnknownRoute}/>
            </Switch>
        </Router>
        <CorprocSnackBar/>
    </React.Fragment>
};


const mapStateToProps = (state: AppState) => ({
    isAdmin: state.user.isAdmin
})
const mapDispatchToProps = {
    showSnackBarMessage: openSnackBar
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
