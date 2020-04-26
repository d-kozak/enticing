import * as Yup from 'yup';
import React, {useState} from 'react';
import Button from '@material-ui/core/Button';
import {TextField} from 'formik-material-ui';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {ApplicationState} from "../../../ApplicationState";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {connect} from "react-redux";
import {Field, Form, Formik} from "formik";
import {putRequest} from "../../../network/requests";
import {LinearProgress} from "@material-ui/core";
import {ChangePasswordCredentials, User} from "../../../entities/user";
import {makeStyles} from "@material-ui/core/styles";
import {getCurrentUserDetails} from "../../../reducers/userDetailsReducer";

const useStyles = makeStyles({
    formField: {
        display: 'block',
        margin: '10px 30px'
    },
    root: {
        padding: '10px'
    }
})

type ChangePasswordDialogProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & { editedUser: User }

const ChangePasswordSchema = Yup.object().shape({
    'oldPassword': Yup.string()
        .min(5)
        .max(64),
    'password1': Yup.string()
        .min(5)
        .max(64),
    'password2': Yup.string()
        .min(5)
        .max(64)
        .oneOf([Yup.ref('password1'), null], 'Passwords do not match')
});

const ChangePasswordDialog = (props: ChangePasswordDialogProps) => {
    const {openSnackbarAction, editedUser, currentUser} = props;
    const [open, setOpen] = useState(false);
    const [progress, setProgress] = useState(false);
    const classes = useStyles();

    if (!currentUser) {
        return <div/>
    }

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const editingMyself = currentUser.id === editedUser.id;

    if (open && !(editingMyself || currentUser.roles.includes("ADMIN"))) {
        openSnackbarAction("You can't edit other people's password")
        return <div/>
    }

    return (
        <div>
            <Button color="primary" variant="contained" onClick={handleClickOpen}>
                Change password
            </Button>
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" className={classes.root}>
                <DialogTitle id="form-dialog-title">Change password</DialogTitle>
                {progress && <LinearProgress/>}
                <DialogContent>
                    <Formik
                        initialValues={{password1: '', password2: '', oldPassword: editingMyself ? '' : 'abcdef'}}
                        validationSchema={ChangePasswordSchema}
                        onSubmit={({password1, oldPassword}, actions) => {
                            const request: ChangePasswordCredentials = {
                                login: editedUser.login,
                                oldPassword: oldPassword,
                                newPassword: password1
                            };
                            setProgress(true)
                            putRequest<void>("/user/password", request)
                                .then(() => {
                                    openSnackbarAction("Password updated")
                                    setProgress(false)
                                    setOpen(false)
                                    actions.setSubmitting(false)
                                })
                                .catch(err => {
                                    setProgress(false)
                                    console.error(err)
                                    actions.setSubmitting(false)
                                    openSnackbarAction("Failed to update password")
                                })
                        }}
                    >
                        {({isSubmitting}) => <Form>
                            {editingMyself &&
                            <Field variant="outlined" type="password" name="oldPassword" label="Old password"
                                   className={classes.formField}
                                   component={TextField}/>}
                            <Field variant="outlined" type="password" name="password1" label="New password"
                                   className={classes.formField}
                                   component={TextField}/>
                            <Field variant="outlined" type="password" name="password2" label="New password again"
                                   className={classes.formField}
                                   component={TextField}/>

                            <DialogActions>
                                <Button onClick={handleClose} color="primary">
                                    Cancel
                                </Button>
                                <Button disabled={isSubmitting} type="submit" color="primary" variant="contained">
                                    Confirm
                                </Button>
                            </DialogActions>
                        </Form>}
                    </Formik>
                </DialogContent>
            </Dialog>
        </div>
    );
}

const mapStateToProps = (state: ApplicationState) => ({
    currentUser: getCurrentUserDetails(state)
});
const mapDispatchToProps = {
    openSnackbarAction,
};

export default connect(mapStateToProps, mapDispatchToProps)(ChangePasswordDialog);