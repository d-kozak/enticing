import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import ExpansionPanel from "@material-ui/core/es/ExpansionPanel";
import Typography from "@material-ui/core/es/Typography";
import ExpansionPanelDetails from "@material-ui/core/es/ExpansionPanelDetails";
import ExpansionPanelSummary from "@material-ui/core/es/ExpansionPanelSummary";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Grid from "@material-ui/core/es/Grid";
import Button from "@material-ui/core/es/Button";
import Paper from "@material-ui/core/es/Paper";
import SettingsDetails from "../settings/SettingsDetails";
import {ApplicationState} from "../../reducers/ApplicationState";
import {searchSettingsSelectedRequestAction} from "../../actions/UserActions";
import {SearchSettings} from "../../entities/SearchSettings";
import {connect} from "react-redux";
import {isAdminSelector, isLoggedInSelector, selectedSearchSettingsSelector} from "../../reducers/selectors";
import Chip from "@material-ui/core/Chip";
import DoneIcon from '@material-ui/icons/Done';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import SettingsForm from "../settings/SettingsForm";
import {addEmptySearchSettingsRequestAction, loadSettingsFromFileAction} from "../../actions/SearchSettingsActions";
import NewSearchSettingsDialog from "../settings/NewSettingsDialog";
import FolderOpenIcon from "@material-ui/icons/FolderOpen";
import {FilePicker} from 'react-file-picker';
import BasicExample from "../corpusformat/BasicExample";

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
        textAlign: 'center'
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
    }
});

export type SearchSettingsPageProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {}

const SearchSettingsPage = (props: SearchSettingsPageProps) => {
    const {classes, isLoggedIn, searchSettings, loadFile, selectSearchSettings, selectedSearchSettings, addSearchSettings, isAdmin} = props;
    const selectedSearchSettingsId = selectedSearchSettings !== null ? selectedSearchSettings.id : "-1";

    return <Paper className={classes.rootElement}>
        <Typography variant="h2" className={classes.settingsTitle}>Search Settings</Typography>
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
                        <BasicExample/>
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
    isAdmin: isAdminSelector(state),
    isLoggedIn: isLoggedInSelector(state),
    searchSettings: state.searchSettings.settings,
    selectedSearchSettings: selectedSearchSettingsSelector(state)
});
const mapDispatchToProps = {
    addSearchSettings: addEmptySearchSettingsRequestAction as () => void,
    loadFile: loadSettingsFromFileAction as (file: File) => void,
    selectSearchSettings: searchSettingsSelectedRequestAction as (settings: SearchSettings, previousSelectedIndex: string, isLoggedIn: boolean) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchSettingsPage))