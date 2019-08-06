import createStyles from "@material-ui/core/es/styles/createStyles";
import {LinearProgress, Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {ApplicationState} from "../../reducers/ApplicationState";
import {connect} from "react-redux";
import React, {useState} from 'react';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import {Field, Form, Formik} from "formik";
import * as Yup from "yup";
import {Switch, TextField} from "formik-material-ui";
import Button from "@material-ui/core/es/Button";
import FormControlLabel from "@material-ui/core/es/FormControlLabel";
import Grid from "@material-ui/core/es/Grid";
import {createNewUserActionRequest} from "../../actions/AdminActions";


const styles = (theme: Theme) => createStyles({
    formField: {
        display: 'block',
        margin: '10px 30px'
    },
    button: {
        margin: '5px'
    }
});

type CreateUserDialogProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {
    isOpen: boolean,
    onClose: () => void
}

const minLenText = 'At least 5 characters, please';
const maxLenText = 'Max 64 characters, please';

const CreateNewUserSchema = Yup.object({
    login: Yup.string()
        .required('Please write your login')
        .min(5, minLenText)
        .max(64, maxLenText),
    password1: Yup.string()
        .required('Please write your password')
        .min(5, minLenText)
        .max(64, maxLenText),
    password2: Yup.string()
        .required('Please write your password again')
        .min(5, minLenText)
        .max(64, maxLenText)
        .oneOf([Yup.ref('password1'), null], 'Passwords do not match')
});

const CreateUserDialog = (props: CreateUserDialogProps) => {
    const {isOpen, onClose, classes, createNewUser} = props;

    const [showProgress, setShowProgress] = useState(false);

    return <Dialog
        open={isOpen}
        onClose={onClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
    >
        {showProgress && <LinearProgress/>}
        <DialogTitle id="alert-dialog-title">Create new user</DialogTitle>
        <DialogContent>
            <Formik
                initialValues={{
                    login: '',
                    password1: '',
                    password2: '',
                    isAdmin: false
                }}
                validationSchema={CreateNewUserSchema}
                onSubmit={(values, actions) => {
                    setShowProgress(true)
                    createNewUser(values.login, values.password1, values.isAdmin ? ["ADMIN"] : [], () => {
                        onClose();
                    }, (errors) => {
                        actions.setSubmitting(false);
                        actions.setErrors(errors)
                        setShowProgress(false);
                    })
                }}
            >
                {({isSubmitting}) => <Form>
                    <Field className={classes.formField} variant="outlined" type="text" name="login" label="Login"
                           component={TextField}/>
                    <Field className={classes.formField} variant="outlined" type="password" name="password1"
                           label="Password"
                           component={TextField}/>
                    <Field className={classes.formField} variant="outlined" type="password" name="password2"
                           label="Password again"
                           component={TextField}/>
                    <FormControlLabel
                        control={<Field className={classes.formField} name="isAdmin"
                                        component={Switch}/>}
                        label="Is admin"
                    />
                    <Grid container justify="flex-end">
                        <Button className={classes.button} onClick={onClose}>
                            Cancel
                        </Button>
                        <Button className={classes.button} variant="contained" color="primary" type="submit"
                                disabled={isSubmitting}>Submit</Button>
                    </Grid>
                </Form>}
            </Formik>
        </DialogContent>
    </Dialog>
};


const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {
    createNewUser: createNewUserActionRequest as (login: string, password: string, roles: Array<string>, onDone: () => void, onError: (errors: any) => void) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(CreateUserDialog));