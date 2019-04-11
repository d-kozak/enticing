import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import {UserSettings} from "../../entities/UserSettings";
import {Field, FieldArray, Form, Formik} from "formik";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import * as Yup from 'yup';
import {RadioGroup, TextField} from "formik-material-ui";
import FormControlLabel from "@material-ui/core/es/FormControlLabel";
import Radio from "@material-ui/core/es/Radio";
import Button from "@material-ui/core/es/Button";
import Typography from "@material-ui/core/es/Typography";
import {Theme} from "@material-ui/core/es";


const styles = (theme: Theme) => createStyles({
    noServerSetErrorMessage: {
        color: theme.palette.error.main
    }
});


export interface SettingsForm extends WithStyles<typeof styles> {
    mappingFiles: Array<string>,
    currentSettings: UserSettings

    submitForm(newSettings: UserSettings): Promise<{}>,
}

// TODO try to find a simpler regex
const ipAddressRegex = /^(((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|localhost)(:\d+)?$/;
const ipAdressFail = 'Please provide a valid ip address';

const SettingsSchema = Yup.object({
    'mappingFile': Yup.string()
        .required('Mapping file is required'),
    'resultsPerPage': Yup.number()
        .positive('Results per page should be a positive number')
        .required('Please specify results per page'),
    'annotationDataServer': Yup.string()
        .matches(ipAddressRegex, ipAdressFail)
        .required('Ip is required'),
    'annotationServer': Yup.string()
        .matches(ipAddressRegex, ipAdressFail)
        .required('Ip is required'),
    'servers': Yup.array(Yup.string()
        .matches(ipAddressRegex, ipAdressFail))
        .min(1)

});

const SettingsForm = (props: SettingsForm) => {
    const {submitForm, currentSettings, mappingFiles, classes} = props;

    const [showProgress, setShowProgress] = useState(false);


    return <div>
        {showProgress && <LinearProgress/>}
        <Formik
            initialValues={currentSettings}
            validationSchema={SettingsSchema}
            onSubmit={(values, actions) => {
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
                <Form>
                    <Typography variant="h4">Mapping file</Typography>
                    <Field name="mappingFile" component={RadioGroup}>
                        {
                            mappingFiles.map((mappingFile, index) => <FormControlLabel key={index} control={<Radio/>}
                                                                                       label={mappingFile}
                                                                                       value={mappingFile}/>)
                        }
                    </Field>
                    <Field variant="outlined" label="Results per page" name="resultsPerPage" component={TextField}
                           type="number"/>
                    <Field variant="outlined" label="Annotation data server" name="annotationDataServer"
                           component={TextField}/>
                    <Field variant="outlined" label="Annotation server" name="annotationServer" component={TextField}/>
                    {errors.servers && !Array.isArray(errors.servers) &&
                    <Typography className={classes.noServerSetErrorMessage} variant="body1">At least one server
                        required</Typography>}
                    <FieldArray name="servers" render={({push, remove}) =>
                        <div>
                            {values.servers.map((server, index) => <div key={index}>
                                <Field name={`servers.${index}`} component={TextField}/>
                                <Button color="secondary" onClick={() => remove(index)}>X</Button>
                            </div>)}
                            <Button color="primary" variant="outlined" onClick={() => push('')}>Add server</Button>
                        </div>
                    }/>
                    {JSON.stringify(values, null, 2)}
                    <Button variant="contained" color="primary" type="submit" disabled={isSubmitting}>Submit</Button>
                </Form>
            }
        </Formik>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SettingsForm)