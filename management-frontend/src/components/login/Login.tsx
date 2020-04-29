import {useHistory} from "react-router";
import React, {useState} from 'react';
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";
import {LinearProgress, Paper, Typography} from "@material-ui/core";
import {Centered} from "../Centered";
import {Field, Form, Formik} from "formik";
import {TextField} from "formik-material-ui";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import * as Yup from "yup";
import {loginRequest} from "../../request/userRequests";
import {UserCredentials} from "../../entities/user";
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

type LoginProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps

const LoginSchema = Yup.object().shape({
    'login': Yup.string()
        .min(5)
        .max(64)
        .required('Login is required'),
    'password': Yup.string()
        .min(5)
        .max(64)
        .required('Password is required'),
});

const Login = (props: LoginProps) => {
    const {loginRequest} = props;
    const [progress, setProgress] = useState(false);

    const classes = useStyles();

    const history = useHistory();

    return <Centered>
        <Paper className={classes.root}>
            <Typography variant="h5">Login</Typography>
            {progress && <LinearProgress/>}
            <Formik
                initialValues={{login: '', password: ''}}
                validationSchema={LoginSchema}
                onSubmit={(credentials, actions) => {
                    setProgress(true)
                    loginRequest(credentials, (success) => {
                        if (success) {
                            history.push("/");
                        } else {
                            setProgress(false)
                            actions.setSubmitting(false)
                        }
                    })
                }}
            >
                {({isSubmitting}) => <Form>
                    <Field variant="outlined" type="text" name="login" label="Login" className={classes.formField}
                           component={TextField}/>
                    <Field variant="outlined" type="password" name="password" label="Password"
                           className={classes.formField}
                           component={TextField}/>
                    <DialogActions>
                        <Button disabled={isSubmitting} type="submit" color="primary" variant="contained">
                            Login
                        </Button>
                    </DialogActions>
                </Form>}
            </Formik>
        </Paper>
    </Centered>
}

const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {
    loginRequest: loginRequest as (credentials: UserCredentials, onDone?: (success: boolean) => void) => void
};


export default connect(mapStateToProps, mapDispatchToProps)(Login);