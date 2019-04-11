import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {UserSettings} from "../../entities/UserSettings";
import {Field, FieldArray, Form, Formik} from "formik";
import * as Yup from 'yup';
import {RadioGroup, TextField} from "formik-material-ui";
import FormControlLabel from "@material-ui/core/es/FormControlLabel";
import Radio from "@material-ui/core/es/Radio";
import Button from "@material-ui/core/es/Button";
import Typography from "@material-ui/core/es/Typography";
import {Theme} from "@material-ui/core/es";
import Divider from '@material-ui/core/Divider';
import Grid from "@material-ui/core/es/Grid";


const styles = (theme: Theme) => createStyles({
    formContent: {
        margin: '10px 15px'
    },
    sectionTitle: {
        marginBottom: '5px'
    },
    settingsSection: {
        margin: '10px 0px'
    },
    noServerSetErrorMessage: {
        color: theme.palette.error.main
    },
    mappingFileSettingsContent: {
        flexDirection: 'row'
    },
    textFieldsGroup: {
        margin: '10px 0px',
        display: 'flex',
        flexWrap: 'wrap'
    },
    textField: {
        margin: '5px 5px'
    },
    addIndexServerButton: {
        marginTop: '10px'
    }
});


export interface SettingsForm extends WithStyles<typeof styles> {
    readOnly?: boolean
    mappingFiles: Array<string>,
    currentSettings: UserSettings,
    submitForm: (newSettings: UserSettings) => Promise<{}>,
    setShowProgress: (showProgress: boolean) => void
}

// TODO try to find a simpler regex
const ipAddressRegex = /^(((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|localhost)(:\d+)?$/;
const ipAddressFail = 'Please provide a valid ip address';

const SettingsSchema = Yup.object({
    'mappingFile': Yup.string()
        .required('Mapping file is required'),
    'resultsPerPage': Yup.number()
        .positive('Results per page should be a positive number')
        .required('Please specify results per page'),
    'annotationDataServer': Yup.string()
        .matches(ipAddressRegex, ipAddressFail)
        .required('Ip is required'),
    'annotationServer': Yup.string()
        .matches(ipAddressRegex, ipAddressFail)
        .required('Ip is required'),
    'servers': Yup.array(Yup.string()
        .matches(ipAddressRegex, ipAddressFail))
        .min(1)

});

const SettingsForm = (props: SettingsForm) => {
    const {readOnly = false, submitForm, currentSettings, setShowProgress, mappingFiles, classes} = props;

    return <Formik
        initialValues={currentSettings}
        validationSchema={SettingsSchema}
        onSubmit={(values, actions) => {
            if (readOnly) {
                throw new Error('Readonly form should never be submitted');
            }
            setShowProgress(true);
            submitForm(values)
                .then(() => {
                    actions.setSubmitting(false);
                    setShowProgress(false);
                    alert('submit');
                })
                .catch(errors => {
                    actions.setErrors(errors);
                    actions.setSubmitting(false);
                    setShowProgress(false);
                    alert('fail');
                })
        }}>
        {({isSubmitting, values, errors}) =>
            <Form className={classes.formContent}>
                <Divider/>
                <div className={classes.settingsSection}>
                    <Typography variant="h5" className={classes.sectionTitle}>Mapping file</Typography>
                    <Field name="mappingFile" className={classes.mappingFileSettingsContent} component={RadioGroup}>
                        {
                            mappingFiles.map((mappingFile, index) => <FormControlLabel key={index}
                                                                                       control={<Radio/>}
                                                                                       disabled={readOnly}
                                                                                       label={mappingFile}
                                                                                       value={mappingFile}/>)
                        }
                    </Field>
                </div>

                <Divider/>
                <div className={classes.settingsSection}>
                    <Typography variant="h5" className={classes.sectionTitle}>Annotation server</Typography>

                    <Field disabled={readOnly} variant="outlined" label="Annotation server" name="annotationServer"
                           component={TextField} className={classes.textField}/>

                    <Field disabled={readOnly} variant="outlined" label="Annotation data server"
                           name="annotationDataServer"
                           component={TextField} className={classes.textField}/>

                </div>

                <Divider/>
                <div className={classes.settingsSection}>
                    <Typography variant="h5" className={classes.sectionTitle}>Index servers</Typography>
                    {errors.servers && !Array.isArray(errors.servers) &&
                    <Typography className={classes.noServerSetErrorMessage} variant="body1">At least one server
                        required</Typography>}
                    <FieldArray name="servers" render={({push, remove}) =>
                        <div>
                            {values.servers.map((server, index) => <div key={index}>
                                <Field disabled={readOnly} name={`servers.${index}`} component={TextField}
                                       className={classes.textField}/>
                                {!readOnly && <Button color="secondary" onClick={() => remove(index)}>X</Button>}
                            </div>)}
                            {!readOnly &&
                            <Button className={classes.addIndexServerButton} color="primary" variant="outlined"
                                    onClick={() => push('')}>Add server</Button>}
                        </div>
                    }/>
                </div>

                <Divider/>
                <div className={classes.settingsSection}>
                    <Typography variant="h5" className={classes.sectionTitle}>Others</Typography>
                    <Field disabled={readOnly} variant="outlined" label="Results per page" name="resultsPerPage"
                           component={TextField}
                           type="number" className={classes.textField}/>
                </div>

                {!readOnly && <Grid container justify="flex-end" alignItems="center">
                    <Grid item>
                        <Button variant="contained" color="primary" type="submit"
                                disabled={isSubmitting}>Submit</Button>
                    </Grid>
                </Grid>}
            </Form>
        }
    </Formik>
};

export default withStyles(styles, {
    withTheme: true
})(SettingsForm)