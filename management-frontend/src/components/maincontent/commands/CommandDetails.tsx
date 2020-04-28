import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {useHistory, useParams} from "react-router";
import {BackButton} from "../../button/BackButton";
import {CircularProgress, Divider, List, ListItem, ListItemText, Typography} from "@material-ui/core";
import {requestCommandInfo} from "../../../reducers/commandsReducer";
import {Centered} from "../../Centered";
import LogViewer from "../../LogViewer";
import {dateTimeToString} from "../../utils/dateUtils";


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
        return <Centered>
            <CircularProgress color="inherit"/>
        </Centered>
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
                <ListItemText primary={`Submitted at: ${dateTimeToString(command.submittedAt)}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Start at: ${dateTimeToString(command.startAt)}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Finished at: ${dateTimeToString(command.finishedAt)}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary="Logs:"/>
            </ListItem>
        </List>
        <LogViewer url={`/command/${commandId}/log`}/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    commands: state.commands
});

const mapDispatchToProps = {
    requestCommandInfo: requestCommandInfo as (id: string) => void
};

export default (connect(mapStateToProps, mapDispatchToProps)(CommandDetails));