import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import {User} from "../../entities/User";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import {LinearProgress} from "@material-ui/core";
import {changePasswordDialogClosedAction} from "../../actions/dialog/ChangePasswordDialogActions";
import {Field, Form, Formik} from "formik";

import * as Yup from "yup";
import {TextField} from "formik-material-ui";
import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core/es";
import withStyles from "@material-ui/core/es/styles/withStyles";

const styles = createStyles({
    formField: {
        display: 'block',
        margin: '10px 10px'
    }
});

export type DeleteUserDialogProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    &
    {
        changePassword: (user: User, newPassword: string) => void
    }

const minLenText = 'At least 5 characters, please';
const maxLenText = 'Max 32 characters, please';

const changePasswordValidation = Yup.object({
    password: Yup.string()
        .required('Please write password')
        .min(5, minLenText)
        .max(32, maxLenText),
    repeat: Yup.string()
        .required('Please write password again')
        .min(5, minLenText)
        .max(32, maxLenText)
        .oneOf([Yup.ref('password'), null], 'Passwords do not match')
});

const DeleteUserDialog = (props: DeleteUserDialogProps) => {
    const {user, changePassword, onClose, showProgress, classes} = props;
    return <div>
        <Dialog
            open={user != null}
            onClose={onClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            {showProgress && <LinearProgress/>}
            <Formik initialValues={{password: '', repeat: ''}}
                    validationSchema={changePasswordValidation}
                    onSubmit={(values) => {
                        changePassword(user!, values.password);
                    }}>
                {({isSubmitting}) => (
                    <Form>
                        <DialogTitle id="alert-dialog-title">Change password</DialogTitle>
                        <DialogContent>
                            <DialogContentText id="alert-dialog-description">
                                <Field className={classes.formField} variant="outlined" type="password" name="password"
                                       label="Password"
                                       component={TextField}/>
                                <Field className={classes.formField} variant="outlined" type="password" name="repeat"
                                       label="Password again"
                                       component={TextField}/>
                            </DialogContentText>
                        </DialogContent>
                        <DialogActions>
                            <Button disabled={isSubmitting} onClick={onClose}>
                                Cancel
                            </Button>
                            <Button type="submit" disabled={isSubmitting} color="primary" variant="contained">
                                Confirm
                            </Button>
                        </DialogActions>
                    </Form>
                )}
            </Formik>
        </Dialog>
    </div>
}

const mapStateToProps = (state: AppState) => ({
    showProgress: state.dialog.changePasswordDialog.showProgress,
    user: state.dialog.changePasswordDialog.user
});
const mapDispatchToProps = {
    onClose: changePasswordDialogClosedAction,
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(DeleteUserDialog));