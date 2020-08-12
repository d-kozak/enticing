import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React, {useCallback} from 'react';
import {useHistory} from "react-router";
import {BackButton} from "../../button/BackButton";
import {CircularProgress, Divider, List, ListItem, ListItemText, ListSubheader, Typography} from "@material-ui/core";
import {Centered} from "../../Centered";
import {useInterval} from "../../../utils/useInterval";
import {requestCorpus} from "../../../reducers/corpusesReducer";
import MaintainerOnly from "../../protectors/MaintainerOnly";
import AddOrEditCorpusDialog from "./AddOrEditCorpusDialog";
import CorpusComponentsTable from "./CorpusComponentsTable";


export type CorpusDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { corpusId: string }

const CorpusDetails = (props: CorpusDetailsProps) => {
    const {corpuses, requestCorpus, corpusId} = props;
    const history = useHistory();
    const refresh = useCallback(() => requestCorpus(corpusId!), [requestCorpus, corpusId])
    const corpus = corpuses.elements[corpusId];
    useInterval(refresh, 2_000);
    if (!corpus) {
        refresh();
        return <Centered>
            <CircularProgress color="inherit"/>
        </Centered>
    }

    return <div>
        <BackButton/>
        <Typography variant="h3">Corpus details</Typography>
        <Divider/>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Name: ${corpus.name}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Components: ${corpus.components}`}/>
            </ListItem>
        </List>
        <Divider/>
        <MaintainerOnly>
            <List component="nav" subheader={<ListSubheader component="div">Actions</ListSubheader>}>
                <ListItem>
                    <AddOrEditCorpusDialog editedCorpus={corpus}/>
                </ListItem>
            </List>
            <Divider/>
        </MaintainerOnly>
        <CorpusComponentsTable corpusId={corpus.id}/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    corpuses: state.corpuses
});

const mapDispatchToProps = {
    requestCorpus: requestCorpus as (id: string) => void
};

export default (connect(mapStateToProps, mapDispatchToProps)(CorpusDetails));