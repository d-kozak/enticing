import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import LinearProgress from "@material-ui/core/es/LinearProgress";
import Typography from "@material-ui/core/es/Typography";
import Paper from "@material-ui/core/es/Paper";
import {UserSettings} from "../../entities/UserSettings";
import SettingsForm from "./SettingsForm";

const styles = createStyles({
    root: {
        width: '80%',
        margin: '20px auto'
    },
    settingsTitle: {
        textAlign: 'center'
    },
    progress: {
        marginBottom: '15px'
    },
    '@media (min-width:500)': {
        root: {
            minWidth: '300px',
            position: 'fixed',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)'
        }
    }
});


export interface SettingsHolderProps extends WithStyles<typeof styles> {
    mappingFiles: Array<string>,
    currentSettings: UserSettings,

    submitForm(newSettings: UserSettings): Promise<{}>,
}

const SettingsHolder = (props: SettingsHolderProps) => {
    const {classes, mappingFiles, currentSettings, submitForm} = props;

    const [showProgress, setShowProgress] = useState(false);

    return <Paper className={classes.root} style={{paddingTop: showProgress ? '0px' : '20px'}}>
        {showProgress && <LinearProgress className={classes.progress}/>}
        <Typography variant="h2" className={classes.settingsTitle}>Settings</Typography>
        <SettingsForm mappingFiles={mappingFiles} currentSettings={currentSettings} submitForm={submitForm}
                      setShowProgress={setShowProgress}/>
    </Paper>
};

export default withStyles(styles, {
    withTheme: true
})(SettingsHolder)