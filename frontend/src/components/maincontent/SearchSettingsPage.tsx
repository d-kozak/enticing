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
import {AppState} from "../../reducers/RootReducer";
import {searchSettingsSelectedRequestAction} from "../../actions/UserActions";
import {SearchSettings} from "../../entities/SearchSettings";
import {connect} from "react-redux";
import {isAdminSelector, selectedSearchSettingsIndexSelector} from "../../reducers/selectors";
import Chip from "@material-ui/core/Chip";
import DoneIcon from '@material-ui/icons/Done';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import SettingsForm from "../settings/SettingsForm";
import {addSearchSettingsRequestAction} from "../../actions/SearchSettingsActions";

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
    const {classes, searchSettings, selectSearchSettings, selectedSearchSettingsIndex, addSearchSettings, isAdmin} = props;
    return <Paper className={classes.rootElement}>
        <Typography variant="h2" className={classes.settingsTitle}>Search Settings</Typography>
        {searchSettings.map((settings, index) => <ExpansionPanel key={settings.id}>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon/>}>
                    <Typography variant="h5">{settings.name}</Typography>
                    {index === selectedSearchSettingsIndex && <Chip
                        icon={<DoneIcon/>}
                        label="Selected"
                        className={classes.chip}
                        color="primary"
                    />}
                    {settings.isPrivate && <Chip
                        icon={<VisibilityOffIcon/>}
                        label="Private"
                        className={classes.chip}
                        color="secondary"
                    />}
                    {settings.isDefault && <Chip label="Default" className={classes.chip}/>}
                </ExpansionPanelSummary>
                <ExpansionPanelDetails className={classes.expansionPanelDetails}>
                    {isAdmin ? <SettingsForm settings={settings}/> : <SettingsDetails settings={settings}/>}
                    <Grid container justify="flex-end" alignItems="center">
                        <Grid item>
                            <Button
                                disabled={index === selectedSearchSettingsIndex}
                                onClick={() => selectSearchSettings(settings)} variant="contained" color="primary"
                                type="submit"><DoneIcon
                                className={classes.iconSmall}/>{index !== selectedSearchSettingsIndex ? 'Select' : 'Already selected'}
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
        </Grid>}
    </Paper>
};

const mapStateToProps = (state: AppState) => ({
    isAdmin: isAdminSelector(state),
    searchSettings: state.searchSettings.settings,
    selectedSearchSettingsIndex: selectedSearchSettingsIndexSelector(state)
});
const mapDispatchToProps = {
    addSearchSettings: addSearchSettingsRequestAction as () => void,
    selectSearchSettings: searchSettingsSelectedRequestAction as (settings: SearchSettings) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchSettingsPage))