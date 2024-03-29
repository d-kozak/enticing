import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import ExpansionPanel from "@material-ui/core/es/ExpansionPanel";
import Typography from "@material-ui/core/es/Typography";
import ExpansionPanelDetails from "@material-ui/core/es/ExpansionPanelDetails";
import ExpansionPanelSummary from "@material-ui/core/es/ExpansionPanelSummary";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import KeyboardBackspaceIcon from '@material-ui/icons/KeyboardBackspace';
import Grid from "@material-ui/core/es/Grid";
import Button from "@material-ui/core/es/Button";
import Paper from "@material-ui/core/es/Paper";
import SettingsDetails from "../settings/SettingsDetails";
import {ApplicationState} from "../../ApplicationState";
import {isLoggedIn, isUserAdmin, selectSearchSettingsRequest} from "../../reducers/UserReducer";
import {SearchSettings} from "../../entities/SearchSettings";
import {connect} from "react-redux";
import {getSelectedSearchSettings} from "../../reducers/selectors";
import Chip from "@material-ui/core/Chip";
import DoneIcon from '@material-ui/icons/Done';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import SettingsForm from "../settings/SettingsForm";
import {addTransientSearchSettings, loadSettingsFromFileRequest} from "../../reducers/SearchSettingsReducer";
import NewSearchSettingsDialog from "../settings/NewSettingsDialog";
import FolderOpenIcon from "@material-ui/icons/FolderOpen";
import {FilePicker} from 'react-file-picker';
import * as H from "history";
import IconButton from "@material-ui/core/es/IconButton";

const styles = (theme: Theme) => createStyles({
    rootElement: {
        width: '80%',
        margin: '20px auto',
        paddingTop: '5px'
    },
    expansionPanelDetails: {
        display: 'block'
    },
    '@media (min-width:1024px)': {
        rootElement: {
            width: '60%'
        }
    },
    settingsTitle: {
        margin: '15px',
        textAlign: 'center',
        flex: '1'
    },
    chip: {
        margin: '0px 5px',
    },
    formButton: {
        margin: '5px'
    },
    grow: {
        flexGrow: 1,
    },
    iconSmall: {
        margin: '0px 5px 0px 0px',
        fontSize: 20,
    },
    backButton: {
        marginLeft: '5px'
    }
});

export type SearchSettingsPageProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {
    history: H.History
}

const SearchSettingsPage = (props: SearchSettingsPageProps) => {
    const {classes, isLoggedIn, searchSettings, loadFile, selectSearchSettings, selectedSearchSettings, addSearchSettings, history, isAdmin} = props;
    const selectedSearchSettingsId = selectedSearchSettings !== null ? selectedSearchSettings.id : "-1";

    return <Paper className={classes.rootElement}>
        <Grid container direction="row">
            <IconButton
                className={classes.backButton}
                key="back"
                aria-label="back"
                color="inherit"
                onClick={() => history.goBack()}
            >
                <KeyboardBackspaceIcon/>
            </IconButton>
            <Typography variant="h2" className={classes.settingsTitle}>Search Settings</Typography>
        </Grid>
        {Object.values(searchSettings)
            .filter(settings => !settings.isTransient)
            .map((settings, index) => <ExpansionPanel key={`${index}-${settings.id}-${settings.name}`}>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon/>}>
                        <Typography variant="h5">{settings.name}</Typography>
                        {settings.id === selectedSearchSettingsId && <Chip
                            icon={<DoneIcon/>}
                            label="Selected"
                            className={classes.chip}
                            color="primary"
                        />}
                        {settings.private && <Chip
                            icon={<VisibilityOffIcon/>}
                            label="Private"
                            className={classes.chip}
                            color="secondary"
                        />}
                        {settings.default && <Chip label="Default" className={classes.chip}/>}
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails className={classes.expansionPanelDetails}>
                        {isAdmin ? <SettingsForm settings={settings}/> : <SettingsDetails settings={settings}/>}
                        <Grid container justify="flex-end" alignItems="center">
                            <Grid item>
                                <Button
                                    disabled={settings.id === selectedSearchSettingsId}
                                    onClick={() => selectSearchSettings(settings, selectedSearchSettingsId, isLoggedIn)}
                                    variant="contained" color="primary"
                                    type="submit"><DoneIcon
                                    className={classes.iconSmall}/>{settings.id !== selectedSearchSettingsId ? 'Select' : 'Already selected'}
                                </Button>
                            </Grid>
                        </Grid>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
            )}
        {isAdmin && <Grid container justify="flex-end" alignItems="center">
            <Grid item>
                <Button className={classes.formButton} onClick={() => addSearchSettings()} variant="contained"
                        color="primary">Add</Button>
            </Grid>
            <Grid>
                <FilePicker
                    extensions={['json']}
                    onChange={loadFile}
                    onError={errMsg => console.error(errMsg)}>
                    <Button className={classes.formButton} onClick={() => addSearchSettings()} variant="contained"
                            color="primary">
                        <FolderOpenIcon className={classes.iconSmall}/>
                        Import
                    </Button>
                </FilePicker>
            </Grid>
        </Grid>}
        <NewSearchSettingsDialog/>
    </Paper>
};

const mapStateToProps = (state: ApplicationState) => ({
    isAdmin: isUserAdmin(state),
    isLoggedIn: isLoggedIn(state),
    searchSettings: state.searchSettings.settings,
    selectedSearchSettings: getSelectedSearchSettings(state)
});
const mapDispatchToProps = {
    addSearchSettings: addTransientSearchSettings as () => void,
    loadFile: loadSettingsFromFileRequest as (file: File) => void,
    selectSearchSettings: selectSearchSettingsRequest as (settings: SearchSettings, previousSelectedIndex: string, isLoggedIn: boolean) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchSettingsPage))