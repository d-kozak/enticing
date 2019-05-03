import React from 'react';
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
import Settings from "./maincontent/Settings";
import UserManagement from "./maincontent/UserManagement";
import AdminRoute from "./routes/AdminRoute";
import {openSnackBar} from "../actions/SnackBarActions";
import {connect} from "react-redux";
import {AppState} from "../reducers/RootReducer";

type AppProps = typeof mapDispatchToProps & ReturnType<typeof mapStateToProps>

const App = ({showSnackBarMessage, isAdmin, progressBarVisible}: AppProps) => {
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
                <Route path="/settings" render={() => <Settings/>}/>
                <AdminRoute path="/users" isAdmin={isAdmin} showSnackBarMessage={showSnackBarMessage}
                            render={() => <UserManagement/>}/>
                <Route path="/:path" render={({match}) => <UnknownRoute path={match.params.path}/>}/>
            </Switch>
        </Router>
        <EnticingSnackbar/>
    </React.Fragment>
};


const mapStateToProps = (state: AppState) => ({
    isAdmin: state.user.isAdmin,
    progressBarVisible: state.progressBar.isVisible
})
const mapDispatchToProps = {
    showSnackBarMessage: openSnackBar
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
