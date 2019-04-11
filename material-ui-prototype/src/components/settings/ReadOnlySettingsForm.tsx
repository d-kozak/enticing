import React from 'react';
import SettingsForm from "./SettingsForm";
import {UserSettings} from "../../entities/UserSettings";


export interface ReadOnlySettingsFormProps {
    mappingFiles: Array<string>,
    currentSettings: UserSettings
}


const errorMsg = 'This form should be readOnly, no callbacks should ever be called from it';

const ReadOnlySettingsForm = (props: ReadOnlySettingsFormProps) => {
    const {mappingFiles, currentSettings} = props;

    const submitForm = () => new Promise((resolve, reject) => {
        reject(errorMsg);
        throw new Error(errorMsg);
    })

    const setShowProgress = () => {
        throw new Error(errorMsg);
    }

    return <SettingsForm readOnly={true} mappingFiles={mappingFiles} currentSettings={currentSettings}
                         submitForm={submitForm} setShowProgress={setShowProgress}/>
};


export default ReadOnlySettingsForm;
