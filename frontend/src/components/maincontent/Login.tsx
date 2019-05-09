import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

import Button from '@material-ui/core/Button';

import React, {useState} from 'react';
import Paper from "@material-ui/core/es/Paper";
import {Theme} from "@material-ui/core/es";
import {Field, Form, Formik, FormikActions} from "formik";
import Grid from '@material-ui/core/Grid';

import * as Yup from 'yup';
import {TextField} from "formik-material-ui";
import {Redirect} from "react-router";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import LinkTo from "../utils/linkTo";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import {loginRequestAction} from "../../actions/UserActions";
import {isLoggedInSelector} from "../../reducers/selectors";

const styles = (theme: Theme) => createStyles({
    mainElement: {
        minWidth: '250px',
        paddingTop: '0px',
        position: 'fixed',
        top: '40%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        textAlign: 'center'
    },
    formField: {
        display: 'block',
        margin: '10px 30px'
    },
    loginButtonsGrid: {
        padding: '10px'
    },
    progress: {
        marginBottom: '15px'
    }
});


export type LoginProps = WithStyles<typeof styles> & typeof mapDispatchToProps & ReturnType<typeof mapStateToProps>

interface LoginFormikProps {
    login: string,
    password: string
}

const minLenText = 'At least 5 characters, please';
const maxLenText = 'Max 32 characters, please';

const LoginSchema = Yup.object().shape({
    login: Yup.string()
        .min(5, minLenText)
        .max(32, maxLenText)
        .required('Required'),
    password: Yup.string()
        .min(5, minLenText)
        .max(32, maxLenText)
        .required('Required'),
});


const Login = (props: LoginProps) => {
    const {classes, login, isLoggedIn} = props;

    if (isLoggedIn) {
        return <Redirect to="/"/>
    }

    const [showProgress, setShowProgress] = useState(false);

    const SignUpLink = LinkTo("/signup");

    return <Paper className={classes.mainElement} style={{paddingTop: showProgress ? '0px' : '20px'}}>
        {showProgress && <LinearProgress className={classes.progress}/>}
        <Typography variant="h4">Sign in</Typography>
        <Formik
            initialValues={{
                login: '',
                password: '',
            }}
            validationSchema={LoginSchema}
            onSubmit={(values: LoginFormikProps, actions: FormikActions<LoginFormikProps>) => {
                setShowProgress(true);
                login(values.login, values.password, errors => {
                    actions.setErrors(errors);
                    actions.setSubmitting(false);
                    setShowProgress(false);
                })
            }}
        >
            {({isSubmitting}) => (
                <Form>
                    <Field className={classes.formField} variant="outlined" type="text" name="login" label="Login"
                           component={TextField}/>
                    <Field className={classes.formField} variant="outlined" type="password" name="password"
                           label="Password"
                           component={TextField}/>

                    <Grid className={classes.loginButtonsGrid} justify="space-between" alignItems="center" container>
                        <Grid item>
                            <Button color="primary" id="foo" component={SignUpLink}>
                                Sign up
                            </Button>
                        </Grid>
                        <Grid item>
                            <Button variant="contained" color="primary" type="submit"
                                    disabled={isSubmitting}>Submit</Button>
                        </Grid>
                    </Grid>
                </Form>
            )}
        </Formik>
    </Paper>
};


const mapStateToProps = (state: AppState) => ({isLoggedIn: isLoggedInSelector(state)})

const mapDispatchToProps = {
    login: loginRequestAction as (login: string, password: string, onError: (errors: { login?: string, password?: string }) => void) => void
}

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(Login))