import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {useHistory, useParams} from "react-router";
import {BackButton} from "../../button/BackButton";
import {CircularProgress, Divider, List, ListItem, ListItemText, Typography} from "@material-ui/core";
import {requestCommandInfo} from "../../../reducers/commandsReducer";


export type CommandDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const CommandDetails = (props: CommandDetailsProps) => {
    const {commands, requestCommandInfo} = props;
    const {commandId} = useParams();
    const history = useHistory();
    if (!commandId) {
        return <div> No command id </div>
    }
    const command = commands.elements[commandId];
    if (!command) {
        requestCommandInfo(commandId);
        return <div>
            <CircularProgress color="inherit"/>
        </div>
    }

    return <div>
        <BackButton/>
        <Typography variant="h3">Command details</Typography>
        <Divider/>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Type: ${command.type}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`State: ${command.state}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Arguments: ${command.arguments}`}/>
            </ListItem>
            <ListItem button onClick={() => history.push(`/user-management/${command.submittedBy}`)}>
                <ListItemText primary={`Submitted by: ${command.submittedBy}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Submitted at: ${command.submittedAt}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Start at: ${command.startAt}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Finished at: ${command.finishedAt}`}/>
            </ListItem>
        </List>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    commands: state.commands
});

const mapDispatchToProps = {
    requestCommandInfo: requestCommandInfo as (id: string) => void
};

export default (connect(mapStateToProps, mapDispatchToProps)(CommandDetails));