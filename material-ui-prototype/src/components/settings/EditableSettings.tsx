import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import SettingsForm from "./SettingsForm";
import {UserSettings} from "../../entities/UserSettings";

const styles = createStyles({});


export interface EditableSettings extends WithStyles<typeof styles> {
}

const EditableSettings = (props: EditableSettings) => {

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

    const mappingFiles = ['mappingA.xml', 'goodMapping.xml', 'crazyMapping.xml'];

    const currentSettings: UserSettings = {
        annotationDataServer: "10.10.10.10:42",
        annotationServer: "192.168.0.25:666",
        mappingFile: 'mappingA.xml',
        resultsPerPage: 42,
        servers: ['localhost:4200']
    };


    return <React.Fragment>
        <SettingsForm submitForm={submitForm} currentSettings={currentSettings} mappingFiles={mappingFiles}/>
    </React.Fragment>
};

export default withStyles(styles, {
    withTheme: true
})(EditableSettings)