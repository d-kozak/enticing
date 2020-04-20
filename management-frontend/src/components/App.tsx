import {Button, createStyles, CssBaseline, Theme} from "@material-ui/core";
import {connect} from "react-redux";
import React from 'react';
import {ApplicationState} from "../ApplicationState";
import {makeStyles} from '@material-ui/core/styles';
import LogTable from "./tables/LogTable";
import {openSnackbarAction} from "../reducers/snackbarReducer";
import EnticingSnackbar from "./snackbar/EnticingSnackbar";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import EnticingAppBar from "./EnticingAppBar";
import EnticingDrawer from "./EnticingDrawer";
import Dashboard from "./maincontent/Dashboard";
import Users from "./maincontent/Users";
import Servers from "./maincontent/Servers";
import Components from "./maincontent/Components";
import Logs from "./maincontent/Logs";
import Perf from "./maincontent/Perf";
import Commands from "./maincontent/Commands";
import Builds from "./maincontent/Builds";

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
    const {openSnackbarAction} = props;
    return <div className={classes.root}>
        <CssBaseline/>
        <Router>
            <EnticingAppBar/>
            <EnticingDrawer/>
            <main className={classes.content}>
                <Switch>
                    <Route path={"/"} exact render={() => <Dashboard/>}/>
                    <Route path={"/user-management"} exact render={() => <Users/>}/>
                    <Route path={"/server"} exact render={() => <Servers/>}/>
                    <Route path={"/component"} exact render={() => <Components/>}/>
                    <Route path={"/log"} exact render={() => <Logs/>}/>
                    <Route path={"/perf"} exact render={() => <Perf/>}/>
                    <Route path={"/command"} exact render={() => <Commands/>}/>
                    <Route path={"/build"} exact render={() => <Builds/>}/>
                </Switch>
                <Button onClick={() => openSnackbarAction("Booo!")}>Booo!</Button>
                <LogTable/>
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