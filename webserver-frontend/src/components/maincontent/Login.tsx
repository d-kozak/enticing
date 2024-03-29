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
import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import {isLoggedIn, loginRequest} from "../../reducers/UserReducer";

const styles = (theme: Theme) => createStyles({
    mainElement: {
        minWidth: '250px',
        textAlign: 'center',
        position: 'fixed',
        top: '40%',
        left: '50%',
        paddingTop: '0px',
        transform: 'translate(-50%, -25%)'
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
    },
    '@media (min-height:800px)': {
        mainElement: {
            transform: 'translate(-50%, -50%)'
        }
    }
});


export type LoginProps = WithStyles<typeof styles> & typeof mapDispatchToProps & ReturnType<typeof mapStateToProps>

interface LoginFormikProps {
    login: string,
    password: string
}

const minLenText = 'At least 5 characters, please';
const maxLenText = 'Max 64 characters, please';

const LoginSchema = Yup.object().shape({
    login: Yup.string()
        .min(5, minLenText)
        .max(64, maxLenText)
        .required('Required'),
    password: Yup.string()
        .min(5, minLenText)
        .max(64, maxLenText)
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


const mapStateToProps = (state: ApplicationState) => ({isLoggedIn: isLoggedIn(state)});

const mapDispatchToProps = {
    login: loginRequest as (login: string, password: string, onError: (errors: { login?: string, password?: string }) => void) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(Login))