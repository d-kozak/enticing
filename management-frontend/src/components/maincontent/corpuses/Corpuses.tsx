import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import {Route, Switch, useRouteMatch} from "react-router";
import CorpusDetails from "./CorpusDetails";
import CorpusTable from "./CorpusTable";
import {Paper} from "@material-ui/core";


export type CorpusesProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Corpuses = (props: CorpusesProps) => {
    const match = useRouteMatch();
    return <Paper>
        <Switch>
            <Route path={`${match.path}/:corpusId`}
                   render={({match}) => <CorpusDetails corpusId={match.params['corpusId']}/>}/>
            <Route path={`${match.path}`}>
                <CorpusTable/>
            </Route>
        </Switch>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Corpuses));