import {Button, createStyles, CssBaseline, Theme} from "@material-ui/core";
import {connect} from "react-redux";
import React from 'react';
import {ApplicationState} from "../ApplicationState";
import {makeStyles} from '@material-ui/core/styles';
import LogTable from "./tables/LogTable";
import {openSnackbarAction} from "../reducers/snackbarReducer";
import EnticingSnackbar from "./snackbar/EnticingSnackbar";
import {BrowserRouter as Router, Switch} from 'react-router-dom';
import EnticingAppBar from "./EnticingAppBar";
import EnticingDrawer from "./EnticingDrawer";

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex'
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3)
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
                    {/*<Route path="/" exact*/}
                    {/*       render={({history}) => <React.Fragment>*/}
                    {/*           <MainPage history={history}/>*/}
                    {/*       </React.Fragment>}/>*/}
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