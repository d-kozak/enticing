import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import React from 'react';
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import * as Yup from 'yup';
import {userSettingsUpdateRequestAction} from "../../actions/UserSettingsActions";
import {Field, Form, Formik} from "formik";

import {UserSettings as UserSettingsModel} from "../../entities/UserSettings";
import {TextField} from "formik-material-ui";
import Grid from "@material-ui/core/es/Grid";
import Button from "@material-ui/core/es/Button";

const styles = (theme: Theme) => createStyles({
    root: {
        position: 'fixed',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)'
    },
    submitButton: {
        margin: '10px'
    },
    title: {
        margin: '10px'
    },
    formField: {
        margin: '0px 10px'
    }
});

type UserSettingsProps = WithStyles<typeof styles> & ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const schema = Yup.object({
    resultsPerPage: Yup.number()
        .min(1, "Cannot show less than one result per page")
        .max(50, "Cannot show more than 50 results per page")
        .required("Required")
});

const UserSettings = (props: UserSettingsProps) => {
    const {classes, userSettings, updateUserSettings} = props;
    return <Paper className={classes.root}>
        <Typography variant="h3" className={classes.title}>User settings</Typography>
        <Formik
            initialValues={userSettings}
            onSubmit={(values, actions) => {
                updateUserSettings(values, () => {
                    actions.setSubmitting(false);
                }, () => {
                });
            }}
            validationSchema={schema}
        >
            {({isSubmitting}) =>
                <Form>
                    <Field variant="outlined" label="Results per page" name="resultsPerPage"
                           className={classes.formField}
                           component={TextField}
                           type="number"/>
                    <Grid container justify="flex-end" alignItems="center">
                        <Grid item>
                            <Button variant="contained" color="primary" type="submit" className={classes.submitButton}
                                    disabled={isSubmitting}>Save</Button>
                        </Grid>
                    </Grid>
                </Form>
            }
        </Formik>
    </Paper>
};


const mapStateToProps = (state: AppState) => ({
    userSettings: state.userSettings
});
const mapDispatchToProps = {
    updateUserSettings: userSettingsUpdateRequestAction as (settings: UserSettingsModel, onDone: () => void, onError: () => void) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(UserSettings));