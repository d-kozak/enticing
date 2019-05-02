import React from 'react';
import CorprocAppBar from "./appbar/CorprocAppBar";
import {CssBaseline} from "@material-ui/core";
import CorprocSnackBar from "./snackbar/CorprocSnackbar";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Login from "./maincontent/Login";
import UnknownRoute from "./maincontent/UnknownRoute";
import SignUp from "./maincontent/SignUp";
import Search from "./maincontent/SearchResultPage";
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
