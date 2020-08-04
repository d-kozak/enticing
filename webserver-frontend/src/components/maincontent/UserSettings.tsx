import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React, {useState} from 'react';
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import * as Yup from 'yup';
import {Field, Form, Formik} from "formik";
import {Switch, TextField} from "formik-material-ui";
import Grid from "@material-ui/core/es/Grid";
import Button from "@material-ui/core/es/Button";
import ChangePasswordDialog from "../changepassworddialog/ChangePasswordDialog";
import {openChangePasswordDialog} from "../../reducers/dialog/ChangePasswordDialogReducer";
import {changePasswordRequest, getUser, isLoggedIn, userSettingsUpdateRequest} from "../../reducers/UserReducer";
import {User} from "../../entities/User";
import {Redirect} from "react-router";
import Divider from "@material-ui/core/Divider";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import * as H from "history";
import LinkTo from "../utils/linkTo";
import {FormControlLabel} from "@material-ui/core/es";

const styles = (theme: Theme) => createStyles({
    root: {
        minWidth: '300px',
        position: 'fixed',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)'
    },
    formButton: {
        margin: '10px'
    },
    title: {
        margin: '10px'
    },
    divider: {
        margin: '0px 10px'
    },
    formField: {
        margin: '0px 10px'
    },
    progress: {
        //  marginBottom: '15px'
    }
});

type UserSettingsProps = WithStyles<typeof styles> & ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    history: H.History
}

const schema = Yup.object({
    resultsPerPage: Yup.number()
        .min(1, "Cannot show less than one result per page")
        .max(50, "Cannot show more than 50 results per page")
        .required("Required")
});

const UserSettings = (props: UserSettingsProps) => {
    const {isLoggedIn, history, user, classes, updateUserSettings, openChangePasswordDialog, changePassword} = props;

    if (!isLoggedIn) {
        return <Redirect to="/"/>
    }

    const [showProgress, setShowProgress] = useState(false);

    return <Paper className={classes.root}>
        {showProgress && <LinearProgress className={classes.progress}/>}
        <Typography variant="h3" className={classes.title}>User settings</Typography>
        <Formik
            initialValues={user.userSettings}
            onSubmit={(values, actions) => {
                setShowProgress(true);

                const newUserInfo = {
                    ...user,
                    userSettings: values
                };
                updateUserSettings(newUserInfo, () => {
                    actions.setSubmitting(false);
                    setShowProgress(false);
                    history.push('/');
                }, () => {
                    actions.setSubmitting(false);
                    setShowProgress(false);
                });
            }}
            validationSchema={schema}
        >
            {({isSubmitting}) =>
                <Form>
                    <Divider className={classes.divider}/>
                    <Typography className={classes.title} variant="h5">Searching</Typography>
                    <Field variant="outlined" label="Results per page" name="resultsPerPage"
                           className={classes.formField}
                           component={TextField}
                           type="number"/>
                    <Divider className={classes.divider}/>
                    <FormControlLabel
                        label="Filter overlapping snippets"
                        className={classes.formField}
                        control={
                            <Field
                                id="filterOverlaps"
                                name="filterOverlaps"
                                component={Switch}
                            />
                        }
                    />
                    <Divider className={classes.divider}/>
                    <Grid container justify="flex-start" alignItems="center">
                        <Grid item>
                            <Button onClick={() => openChangePasswordDialog(user)} variant="contained" color="secondary"
                                    className={classes.formButton}>Change password</Button>
                        </Grid>
                    </Grid>
                    <Grid container justify="flex-end" alignItems="center">
                        <Grid item>
                            <Button component={LinkTo("/")}>Cancel</Button>
                        </Grid>
                        <Grid item>
                            <Button variant="contained" color="primary" type="submit" className={classes.formButton}
                                    disabled={isSubmitting}>Save</Button>
                        </Grid>
                    </Grid>
                </Form>
            }
        </Formik>
        <ChangePasswordDialog changePassword={changePassword} askForOldPassword={true}/>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({
    isLoggedIn: isLoggedIn(state),
    user: getUser(state)
});
const mapDispatchToProps = {
    openChangePasswordDialog: openChangePasswordDialog,
    changePassword: changePasswordRequest as (user: User, oldPassword: String, newPassword: string, onError: (errors: any) => void) => void,
    updateUserSettings: userSettingsUpdateRequest as (settings: User, onDone: () => void, onError: () => void) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(UserSettings));