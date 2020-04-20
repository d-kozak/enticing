import {CssBaseline, Theme} from "@material-ui/core";
import {connect} from "react-redux";
import React from 'react';
import {ApplicationState} from "../ApplicationState";
import {makeStyles} from '@material-ui/core/styles';
import DummyTable from "./DummyTable";

const useStyles = (theme: Theme) => makeStyles({});

type AppProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const App = (props: AppProps) => {
    const {} = props;
    return <div>
        <CssBaseline/>
        Hello
        <DummyTable/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(App);