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
import Dashboard from "./maincontent/dashboard/Dashboard";
import Users from "./maincontent/users/Users";
import Servers from "./maincontent/servers/Servers";
import Components from "./maincontent/components/Components";
import Logs from "./maincontent/logs/Logs";
import Perf from "./maincontent/perf/Perf";
import Commands from "./maincontent/commands/Commands";
import Builds from "./maincontent/builds/Builds";
import Login from "./login/Login";

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
            <EnticingDrawer/>
            <main className={classes.content}>
                <Switch>
                    <Route path={"/"} exact render={() => <Dashboard/>}/>
                    <Route path={"/login"} render={() => <Login/>}/>
                    <Route path={"/user-management"} render={() => <Users/>}/>
                    <Route path={"/server"} render={() => <Servers/>}/>
                    <Route path={"/component"} render={() => <Components/>}/>
                    <Route path={"/log"} render={() => <Logs/>}/>
                    <Route path={"/perf"} render={() => <Perf/>}/>
                    <Route path={"/command"} render={() => <Commands/>}/>
                    <Route path={"/build"} render={() => <Builds/>}/>
                </Switch>
            </main>
        </Router>
        <EnticingSnackbar/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {
    openSnackbarAction
};

export default connect(mapStateToProps, mapDispatchToProps)(App);