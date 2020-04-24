import {createStyles, CssBaseline, Theme} from "@material-ui/core";
import {connect} from "react-redux";
import React from 'react';
import {ApplicationState} from "../ApplicationState";
import {makeStyles} from '@material-ui/core/styles';
import {openSnackbarAction} from "../reducers/snackbarReducer";
import EnticingSnackbar from "./snackbar/EnticingSnackbar";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import EnticingAppBar from "./EnticingAppBar";
import EnticingDrawer from "./EnticingDrawer";
import Servers from "./maincontent/servers/Servers";
import Login from "./login/Login";
import {isLoggedIn} from "../reducers/userDetailsReducer";
import AuthenticatedRoute from "./routes/AuthenticatedRoute";
import Components from "./maincontent/components/Components";
import Dashboard from "./maincontent/dashboard/Dashboard";
import AdminRoute from "./routes/AdminRoute";
import Users from "./maincontent/users/Users";
import UserDetails from "./maincontent/users/UserDetails";
import Logs from "./maincontent/logs/Logs";
import Perf from "./maincontent/perf/Perf";
import Commands from "./maincontent/commands/Commands";
import Builds from "./maincontent/builds/Builds";
import AuthenticatedOnly from "./protectors/AuthenticatedOnly";

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex'
    },
    content: {
        flexGrow: 1,
        paddingTop: theme.spacing(10),
        paddingLeft: theme.spacing(3),
        paddingRight: theme.spacing(3),
        paddingBottom: theme.spacing(3)
    }
}));

type AppProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const App = (props: AppProps) => {
    const classes = useStyles();
    return <div className={classes.root}>
        <CssBaseline/>
        <Router>
            <EnticingAppBar/>
            <AuthenticatedOnly>
                <EnticingDrawer/>
            </AuthenticatedOnly>
            <main className={classes.content}>
                <Switch>
                    <AuthenticatedRoute path={"/"} exact={true}> <Dashboard/> </AuthenticatedRoute>
                    <Route path={"/login"} render={() => <Login/>}/>
                    <AdminRoute path={"/user-management"}> <Users/> </AdminRoute>
                    <AuthenticatedRoute path={"/user-details/:userId"}> <UserDetails/> </AuthenticatedRoute>
                    <AuthenticatedRoute path={"/server"} exact={false}> <Servers/> </AuthenticatedRoute>
                    <AuthenticatedRoute path={"/component"} exact={false}> <Components/> </AuthenticatedRoute>
                    <AuthenticatedRoute path={"/log"} exact={false}> <Logs/> </AuthenticatedRoute>
                    <AuthenticatedRoute path={"/perf"} exact={false}> <Perf/> </AuthenticatedRoute>
                    <AuthenticatedRoute path={"/command"} exact={false}> <Commands/> </AuthenticatedRoute>
                    <AuthenticatedRoute path={"/build"} exact={false}> <Builds/> </AuthenticatedRoute>
                </Switch>
            </main>
        </Router>
        <EnticingSnackbar/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    isLoggedIn: isLoggedIn(state)
});
const mapDispatchToProps = {
    openSnackbarAction
};

export default connect(mapStateToProps, mapDispatchToProps)(App);