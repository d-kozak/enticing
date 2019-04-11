import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {DefaultUserSettings} from "../../entities/UserSettings";
import ExpansionPanel from "@material-ui/core/es/ExpansionPanel";
import Typography from "@material-ui/core/es/Typography";
import ExpansionPanelDetails from "@material-ui/core/es/ExpansionPanelDetails";
import ExpansionPanelSummary from "@material-ui/core/es/ExpansionPanelSummary";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Grid from "@material-ui/core/es/Grid";
import Button from "@material-ui/core/es/Button";
import SettingsForm from "./SettingsForm";
import Paper from "@material-ui/core/es/Paper";


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


const handleFormSubmit = () => {
    const msg = 'should NEVER be called!';
    alert(msg);
    return new Promise((resolve, reject) => reject(msg));
};

export interface DefaultSettingsPicker extends WithStyles<typeof styles> {
    defaultSettingsOptions: Array<DefaultUserSettings>,
    mappingFiles: Array<string>

    settingsSelected(index: number): void
}

const DefaultSettingsPicker = (props: DefaultSettingsPicker) => {
    const {mappingFiles, settingsSelected, defaultSettingsOptions, classes} = props;
    return <Paper className={classes.rootElement}>
        <Typography variant="h2" className={classes.settingsTitle}>Settings</Typography>
        <Typography variant="body1" className={classes.settingsText}>You can choose one of the predefined
            settings</Typography>
        {defaultSettingsOptions.map((settings, index) => <ExpansionPanel key={index}>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon/>}>
                    <Typography variant="h4">{settings.title}</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails className={classes.expansionPanelDetails}>
                    <Typography variant="body1">{settings.description}</Typography>

                    <SettingsForm submitForm={handleFormSubmit} currentSettings={settings} mappingFiles={mappingFiles}/>

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