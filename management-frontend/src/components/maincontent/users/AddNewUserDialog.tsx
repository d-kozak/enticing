import * as Yup from 'yup';
import React, {useState} from 'react';
import Button from '@material-ui/core/Button';
import {Checkbox, TextField} from 'formik-material-ui';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {ApplicationState} from "../../../ApplicationState";
import {addUser} from "../../../reducers/usersReducer";
import {connect} from "react-redux";
import {Field, Form, Formik} from "formik";
import {postRequest} from "../../../network/requests";
import {FormControlLabel, LinearProgress} from "@material-ui/core";
import {CreateUserRequest, User} from "../../../entities/user";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles({
    formField: {
        display: 'block',
        margin: '10px 30px'
    },
    root: {
        padding: '10px'
    }
})

type AddNewUserDialogProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps

const AddNewUserSchema = Yup.object().shape({
    'login': Yup.string()
        .min(5)
        .max(64)
        .required('Login is required'),
    'password1': Yup.string()
        .min(5)
        .max(64),
    'password2': Yup.string()
        .min(5)
        .max(64)
        .oneOf([Yup.ref('password1'), null], 'Passwords do not match')
});

const AddNewUserDialog = (props: AddNewUserDialogProps) => {
    const {addUser} = props;
    const [open, setOpen] = useState(false);
    const [progress, setProgress] = useState(false);

    const classes = useStyles();

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <Button color="primary" variant="contained" onClick={handleClickOpen}>
                Add new user
            </Button>
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" className={classes.root}>
                <DialogTitle id="form-dialog-title">Add new user</DialogTitle>
                {progress && <LinearProgress/>}
                <DialogContent>
                    <Formik
                        initialValues={{login: '', password: '', password2: '', isAdmin: false, isMaintainer: false}}
                        validationSchema={AddNewUserSchema}
                        onSubmit={({login, password, isAdmin, isMaintainer}, actions) => {
                            const request: CreateUserRequest = {
                                login,
                                password,
                                roles: []
                            };
                            if (isAdmin) request.roles.push("ADMIN")
                            if (isMaintainer) request.roles.push("PLATFORM_MAINTAINER")
                            setProgress(true)
                            postRequest<User>("/user/add", request)
                                .then(user => {
                                    setProgress(false)
                                    addUser(user)
                                    setOpen(false)
                                    actions.setSubmitting(false)
                                })
                                .catch(err => {
                                    setProgress(false)
                                    console.error(err)
                                    actions.setSubmitting(false)
                                })
                        }}
                    >
                        {({isSubmitting}) => <Form>
                            <Field variant="outlined" type="text" name="login" label="Login"
                                   className={classes.formField}
                                   component={TextField}/>
                            <Field variant="outlined" type="password" name="password1" label="Password"
                                   className={classes.formField}
                                   component={TextField}/>
                            <Field variant="outlined" type="password" name="password2" label="Password again"
                                   className={classes.formField}
                                   component={TextField}/>

                            <FormControlLabel
                                control={<Field variant="outlined" name="isMaintainer" label="Platform Maintainer"
                                                component={Checkbox}/>}
                                label="Platform Maintainer"
                                className={classes.formField}
                            />

                            <FormControlLabel
                                control={<Field variant="outlined" name="isAdmin" label="Admin" component={Checkbox}/>}
                                label="Admin"
                                className={classes.formField}
                            />

                            <DialogActions>
                                <Button onClick={handleClose} color="primary">
                                    Cancel
                                </Button>
                                <Button disabled={isSubmitting} type="submit" color="primary" variant="contained">
                                    Add
                                </Button>
                            </DialogActions>
                        </Form>}
                    </Formik>
                </DialogContent>
            </Dialog>
        </div>
    );
}

const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {
    addUser
};

export default connect(mapStateToProps, mapDispatchToProps)(AddNewUserDialog);