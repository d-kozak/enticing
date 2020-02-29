import React, {useEffect} from 'react';
import EnticingAppBar from "./appbar/EnticingAppBar";
import {CssBaseline} from "@material-ui/core";
import EnticingSnackbar from "./snackbar/EnticingSnackbar";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Login from "./maincontent/Login";
import UnknownRoute from "./maincontent/UnknownRoute";
import SignUp from "./maincontent/SignUp";
import SearchPage from "./maincontent/SearchPage";
import MainPage from "./maincontent/MainPage";
import UserManagement from "./maincontent/UserManagement";
import AdminRoute from "./routes/AdminRoute";
import {connect} from "react-redux";
import {ApplicationState} from "../ApplicationState";
import UserSettings from "./maincontent/UserSettings";
import SelectSearchSettingsPage from "./maincontent/SearchSettingsPage";
import {attemptLoginRequest, isUserAdmin} from "../reducers/UserReducer";
import UserRoute from "./routes/UserRoute";
import {openSnackbar} from "../reducers/SnackBarReducer";

type AppProps = typeof mapDispatchToProps & ReturnType<typeof mapStateToProps>

const App = ({loginAttempt, showSnackBarMessage, isAdmin, progressBarVisible}: AppProps) => {

    useEffect(() => {
        loginAttempt();
    }, []);


    return <React.Fragment>
        <CssBaseline/>
        <Router>
            <EnticingAppBar/>
            {progressBarVisible && <LinearProgress color="secondary"/>}

            <Switch>
                <Route path="/" exact
                       render={({history}) => <React.Fragment>
                           <MainPage history={history}/>
                       </React.Fragment>}/>
                <Route path="/search"
                       render={({history, location}) => <SearchPage history={history} location={location}/>}/>
                <Route path="/login" render={({}) => <Login/>}/>
                <Route path="/signup" render={() => <SignUp/>}/>
                <Route path="/search-settings" render={({history}) => <SelectSearchSettingsPage history={history}/>}/>
                <UserRoute path="/settings" render={({history}) => <UserSettings history={history}/>}/>
                <AdminRoute path="/users" isAdmin={isAdmin} showSnackBarMessage={showSnackBarMessage}
                            render={() => <UserManagement/>}/>
                <Route path="/:path" render={({match}) => <UnknownRoute path={match.params.path}/>}/>
            </Switch>
        </Router>
        <EnticingSnackbar/>
    </React.Fragment>
};


const mapStateToProps = (state: ApplicationState) => ({
    isAdmin: isUserAdmin(state),
    progressBarVisible: state.progressBar.isVisible
});
const mapDispatchToProps = {
    showSnackBarMessage: openSnackbar,
    loginAttempt: attemptLoginRequest as () => void
};

export default connect(mapStateToProps, mapDispatchToProps)(App);