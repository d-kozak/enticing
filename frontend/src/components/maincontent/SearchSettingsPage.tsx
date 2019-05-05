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
import {selectedSearchSettingsIndexSelector} from "../../reducers/selectors";
import Chip from "@material-ui/core/Chip";
import DoneIcon from '@material-ui/icons/Done';
import SettingsForm from "../settings/SettingsForm";

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
    }
});

export type SearchSettingsPageProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {}

const SearchSettingsPage = (props: SearchSettingsPageProps) => {
    const {classes, searchSettings, selectSearchSettings, selectedSearchSettingsIndex, isAdmin} = props;
    return <Paper className={classes.rootElement}>
        <Typography variant="h2" className={classes.settingsTitle}>Search Settings</Typography>
        {searchSettings.map((settings, index) => <ExpansionPanel key={index}>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon/>}>
                    <Typography variant="h5">{settings.name}</Typography>
                    {index === selectedSearchSettingsIndex && <Chip
                        icon={<DoneIcon/>}
                        label="Selected"
                        className={classes.chip}
                        color="primary"
                    />}
                    {settings.isPrivate && <Chip
                        label="Private"
                        className={classes.chip}
                        color="secondary"
                    />}
                </ExpansionPanelSummary>
                <ExpansionPanelDetails className={classes.expansionPanelDetails}>
                    {isAdmin ? <SettingsForm settings={settings}/> : <SettingsDetails settings={settings}/>}
                    <Grid container justify="flex-end" alignItems="center">
                        <Grid item>
                            <Button
                                disabled={index === selectedSearchSettingsIndex}
                                    onClick={() => selectSearchSettings(settings)} variant="contained" color="primary"
                                    type="submit">{index !== selectedSearchSettingsIndex ? 'Select' : 'Already selected'}</Button>
                        </Grid>
                    </Grid>
                </ExpansionPanelDetails>
            </ExpansionPanel>
        )}
    </Paper>
};

const mapStateToProps = (state: AppState) => ({
    isAdmin: state.user.isAdmin,
    searchSettings: state.searchSettings.settings,
    selectedSearchSettingsIndex: selectedSearchSettingsIndexSelector(state)
});
const mapDispatchToProps = {
    selectSearchSettings: searchSettingsSelectedRequestAction as (settings: SearchSettings) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchSettingsPage))