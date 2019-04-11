import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {DefaultUserSettings, UserSettings} from "../../entities/UserSettings";
import SettingsForm from "../settings/SettingsForm";
import DefaultSettingsPicker from "../settings/DefaultSettingsPicker";

const styles = createStyles({
    settings: {
        minWidth: '300px',
        position: 'fixed',
        top: '52%',
        left: '50%',
        transform: 'translate(-50%, -50%)'
    }
});


export interface SettingsProps extends WithStyles<typeof styles> {
    isLoggedIn: boolean
}

const Settings = (props: SettingsProps) => {
    const {isLoggedIn, classes} = props;

    const submitForm = (newSettings: UserSettings) => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                if (Math.random() > 0.2) {
                    resolve();
                } else {
                    reject('Error');
                }
            }, 2000);
        });
    }

    const handleSettingsSelected = (index: number) => {
        alert(`Settings ${defaultSettingsOptions[index].title} selected `);
    };

    const mappingFiles = ['mappingA.xml', 'goodMapping.xml', 'crazyMapping.xml'];

    const defaultSettingsOptions: Array<DefaultUserSettings> = [
        {
            title: 'Basic',
            description: 'Initial most intuitive config',
            annotationDataServer: "10.10.10.10:42",
            annotationServer: "192.168.0.25:666",
            mappingFile: 'mappingA.xml',
            resultsPerPage: 42,
            servers: ['localhost:4200']
        },
        {
            title: 'Fast',
            description: 'High performance config',
            annotationDataServer: "10.10.10.10:42",
            annotationServer: "192.168.0.25:666",
            mappingFile: 'mappingA.xml',
            resultsPerPage: 42,
            servers: ['localhost:4200', 'localhost:9000', '8.8.8.8:2000']
        }
    ];

    const currentSettings = defaultSettingsOptions[0];

    if (isLoggedIn) {
        return <SettingsForm className={classes.settings} submitForm={submitForm} currentSettings={currentSettings}
                             mappingFiles={mappingFiles}/>
    } else {
        return <DefaultSettingsPicker defaultSettingsOptions={defaultSettingsOptions} mappingFiles={mappingFiles}
                                      settingsSelected={handleSettingsSelected}/>
    }
};

export default withStyles(styles, {
    withTheme: true
})(Settings)