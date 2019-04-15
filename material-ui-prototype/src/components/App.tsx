import React from 'react';
import CorprocAppBar from "../containers/appbar/CorprocAppBar";
import {CssBaseline} from "@material-ui/core";
import CorprocSnackBar from "../containers/snackbar/CorprocSnackbar";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Login from "../containers/maincontent/Login";
import UnknownRoute from "./maincontent/UnknownRoute";
import SignUp from "../containers/maincontent/SignUp";
import Search from "../containers/maincontent/SearchResultPage";
import MainPage from "../containers/maincontent/MainPage";
import Settings from "../containers/maincontent/Settings";
import UserManagement from "./maincontent/UserManagement";
import AdminRoute from "./routes/AdminRoute";
import {openSnackBar} from "../actions/SnackBarActions";
import {connect} from "react-redux";
import {AppState} from "../AppState";

interface AppProps {
    showSnackBarMessage: (message: string) => void
    isAdmin: boolean,
    progressBarVisible: boolean
}

const App = ({showSnackBarMessage, isAdmin, progressBarVisible}: AppProps) => {
    return <React.Fragment>
        <CssBaseline/>
        <Router>
            <CorprocAppBar/>
            {progressBarVisible && <LinearProgress color="secondary"/>}

            <Switch>
                <Route path="/" exact
                       render={({history}) => <React.Fragment>
                           <MainPage history={history}/>
                       </React.Fragment>}/>
                <Route path="/search"
                       render={() => <Search/>}/>
                <Route path="/login" render={({}) => <Login/>}/>
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
    isAdmin: state.user.isAdmin,
    progressBarVisible: state.progressBar.isVisible
})
const mapDispatchToProps = {
    showSnackBarMessage: openSnackBar
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
