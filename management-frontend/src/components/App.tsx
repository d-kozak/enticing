import {CssBaseline} from "@material-ui/core";
import {connect} from "react-redux";
import React from 'react';
import {ApplicationState} from "../ApplicationState";
import {makeStyles} from '@material-ui/core/styles';
import DummyTable from "./DummyTable";
import LogTable from "./LogTable";

const useStyles = makeStyles({});

type AppProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const App = (props: AppProps) => {
    const {} = props;
    return <div>
        <CssBaseline/>
        Hello
        <DummyTable/>
        <LogTable/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(App);