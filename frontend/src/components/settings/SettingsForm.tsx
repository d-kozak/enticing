import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";

import React, {useState} from 'react';
import {Field, FieldArray, Form, Formik} from "formik";
import * as Yup from 'yup';
import {Switch, TextField} from "formik-material-ui";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/es/Typography";
import {Theme} from "@material-ui/core/es";
import Divider from '@material-ui/core/Divider';
import Grid from "@material-ui/core/es/Grid";
import {SearchSettings} from "../../entities/SearchSettings";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import SaveIcon from '@material-ui/icons/Save';
import DeleteIcon from '@material-ui/icons/Delete';
import {ApplicationState} from "../../reducers/ApplicationState";
import withStyles from "@material-ui/core/styles/withStyles";
import {connect} from "react-redux";
import {
    changeDefaultSearchSettingsRequestAction,
    removeSearchSettingsRequestAction,
    saveNewSearchSettingsAction,
    searchSettingsAddingCancelledAction,
    updateSearchSettingsRequestAction
} from '../../actions/SearchSettingsActions'
import {downloadFile} from '../../utils/file';

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
    },
    formButton: {
        margin: '5px'
    },
    progress: {},
    iconSmall: {
        margin: '0px 5px 0px 0px',
        fontSize: 20,
    }
});


export type SettingsFormProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {
    settings: SearchSettings
}

// TODO try to find simpler regexes, if possible
const ipAddressRegex = /^(((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|localhost)(:\d+)?$/;
const ipAddressFail = 'Please provide a valid ip address';

const urlRegex = /((([-\w]+\.)+[\w-]+)|((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|localhost)(:\d+)?([\w-/]*)$/;
const urlRegexFail = 'Please provide a valid url';

const SettingsSchema = Yup.object().shape({
    'name': Yup.string().required('Name cannot be empty'),
    'annotationDataServer': Yup.string()
        .matches(urlRegex, urlRegexFail)
        .required('URL is required'),
    'annotationServer': Yup.string()
        .matches(urlRegex, urlRegexFail)
        .required('URL is required'),
    'servers': Yup.array(Yup.string()
        .matches(ipAddressRegex, ipAddressFail))
        .min(1)
});
const SettingsForm = (props: SettingsFormProps) => {
    const {saveSettings, updateSettings, makeDefault, settings, classes, removeSettings, cancelAdding} = props;

    const [showProgress, setShowProgress] = useState(false);

    return <Formik
        initialValues={settings}
        validationSchema={SettingsSchema}
        onSubmit={(values, actions) => {
            const submitAction = settings.isTransient ? saveSettings : updateSettings;
            setShowProgress(true);
            submitAction(values, () => {
                setShowProgress(false);
                actions.setSubmitting(false);
            }, (errors) => {
                actions.setErrors(errors);
                setShowProgress(false);
                actions.setSubmitting(false);
            });
        }}>
        {({isSubmitting, values, errors}) =>
            <Form className={classes.formContent}>
                {showProgress && <LinearProgress className={classes.progress}/>}
                <div className={classes.settingsSection}>
                    <Typography variant="h5" className={classes.sectionTitle}>Common</Typography>
                    <Field variant="outlined" label="Name" name="name"
                           component={TextField} className={classes.textField}/>
                    <br/>
                    <Typography variant="body1">Is private?</Typography>
                    <Field label="Private" name="private" component={Switch}/>
                </div>
                <Divider/>

                <div className={classes.settingsSection}>
                    <Typography variant="h5" className={classes.sectionTitle}>Annotation server</Typography>

                    <Field variant="outlined" label="Annotation server" name="annotationServer"
                           component={TextField} className={classes.textField}/>

                    <Field variant="outlined" label="Annotation data server"
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
                                <Field name={`servers.${index}`} component={TextField}
                                       className={classes.textField}/>
                                <Button color="secondary" onClick={() => remove(index)}>X</Button>
                            </div>)}

                            <Button className={classes.addIndexServerButton} color="primary" variant="outlined"
                                    onClick={() => push('')}>Add server</Button>
                        </div>
                    }/>
                </div>

                <Grid container justify="flex-end" alignItems="center">
                    {settings.isTransient &&
                    <Button onClick={cancelAdding} className={classes.formButton} disabled={isSubmitting}>
                        Cancel
                    </Button>}
                    <Grid item>
                        <Button className={classes.formButton} variant="contained" color="primary" type="submit"
                                disabled={isSubmitting}>
                            Save
                        </Button>
                    </Grid>

                    {!settings.isTransient && <Grid item>
                        <Button
                            onClick={() => {
                                setShowProgress(true);
                                removeSettings(settings, () => {
                                }, () => setShowProgress(false));
                            }} className={classes.formButton} variant="contained" color="secondary">
                            <DeleteIcon className={classes.iconSmall}/>
                            Delete
                        </Button>
                    </Grid>}
                    {!settings.default && !settings.isTransient && <Grid item>
                        <Button
                            onClick={() => {
                                setShowProgress(true);
                                makeDefault(settings, () => setShowProgress(false), () => setShowProgress(false));
                            }}
                            className={classes.formButton} color="primary">
                            Make default
                        </Button>
                    </Grid>}
                    {!settings.isTransient && <Grid item>
                        <Button
                            onClick={() => downloadFile(`${settings.name.replace(/\s+/, '_')}.search.settings.json`, JSON.stringify(settings, ["name", "annotationDataServer", "annotationServer", "servers"], 4))}
                            className={classes.formButton} color="primary">
                            <SaveIcon className={classes.iconSmall}/>
                            Download
                        </Button>
                    </Grid>}
                </Grid>
            </Form>
        }
    </Formik>
};

const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {
    saveSettings: saveNewSearchSettingsAction as (newSettings: SearchSettings, onDone: () => void, onError: (errors: any) => void) => void,
    updateSettings: updateSearchSettingsRequestAction as (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void) => void,
    removeSettings: removeSearchSettingsRequestAction as (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void) => void,
    makeDefault: changeDefaultSearchSettingsRequestAction as (settings: SearchSettings, onDone: () => void, onError: (errors: any) => void) => void,
    cancelAdding: searchSettingsAddingCancelledAction
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SettingsForm))