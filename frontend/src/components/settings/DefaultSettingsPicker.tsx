import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
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
import SettingsDetails from "./SettingsDetails";
import {UserSettings} from "../../entities/UserSettings";
import {SearchSettings} from "../../entities/SearchSettings";


const styles = createStyles({
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
    settingsText: {
        margin: '5px'
    }
});

export interface DefaultSettingsPicker extends WithStyles<typeof styles> {
    defaultSettingsOptions: Array<UserSettings & SearchSettings>,
    settingsSelected(index: number): void
}

const DefaultSettingsPicker = (props: DefaultSettingsPicker) => {
    const {settingsSelected, defaultSettingsOptions, classes} = props;
    return <Paper className={classes.rootElement}>
        <Typography variant="h2" className={classes.settingsTitle}>Settings</Typography>
        <Typography variant="body1" className={classes.settingsText}>You can choose one of the predefined
            settings</Typography>
        {defaultSettingsOptions.map((settings, index) => <ExpansionPanel key={index}>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon/>}>
                    <Typography variant="h5">{settings.name}</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails className={classes.expansionPanelDetails}>
                    <SettingsDetails settings={settings}/>
                    <Grid container justify="flex-end" alignItems="center">
                        <Grid item>
                            <Button onClick={() => settingsSelected(index)} variant="contained" color="primary"
                                    type="submit">Apply</Button>
                        </Grid>
                    </Grid>
                </ExpansionPanelDetails>
            </ExpansionPanel>
        )}
    </Paper>
};

export default withStyles(styles, {
    withTheme: true
})(DefaultSettingsPicker)