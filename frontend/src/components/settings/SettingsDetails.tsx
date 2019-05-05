import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import React, {useState} from 'react';
import List from "@material-ui/core/List";
import ListItemText from "@material-ui/core/ListItemText";
import ListItem from "@material-ui/core/ListItem";
import Collapse from "@material-ui/core/Collapse";
import {ExpandLess, ExpandMore} from "@material-ui/icons";
import {SearchSettings} from "../../entities/SearchSettings";


const styles = (theme: Theme) => createStyles({});

type SettingsDetailsProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {
    settings: SearchSettings
}

const SettingsDetails = (props: SettingsDetailsProps) => {
    const {settings} = props;

    const [serversOpen, setServersOpen] = useState(true);

    return <List
        component="nav">
        <ListItem><ListItemText>Annotation server: {settings.annotationServer}</ListItemText></ListItem>
        <ListItem><ListItemText>Annotation data server: {settings.annotationDataServer}</ListItemText></ListItem>
        <ListItem button onClick={() => setServersOpen(!serversOpen)}>
            <ListItemText>Index servers</ListItemText>
            {serversOpen ? <ExpandLess/> : <ExpandMore/>}
        </ListItem>
        <Collapse in={serversOpen} timeout="auto" unmountOnExit>
            <List disablePadding>
                {settings.servers.map((server, index) => <ListItem key={index}>
                    <ListItemText>{server}</ListItemText>
                </ListItem>)}
            </List>
        </Collapse>
    </List>
};


const mapStateToProps = (state: AppState) => ({});
const mapDispatchToProps = {};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(SettingsDetails));