import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {DefaultUserSettings, UserSettings} from "../../entities/UserSettings";
import DefaultSettingsPicker from "../settings/DefaultSettingsPicker";
import SettingsHolder from "../settings/SettingsHolder";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";

const styles = createStyles({});


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
        return <SettingsHolder submitForm={submitForm} currentSettings={currentSettings}
                               mappingFiles={mappingFiles}/>
    } else {
        return <DefaultSettingsPicker defaultSettingsOptions={defaultSettingsOptions} mappingFiles={mappingFiles}
                                      settingsSelected={handleSettingsSelected}/>
    }
};


const mapStateToProps = (state: AppState) => ({
    isLoggedIn: state.user.isLoggedIn
});

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps)(Settings))