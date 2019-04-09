import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

import Button from '@material-ui/core/Button';

import React from 'react';
import Paper from "@material-ui/core/es/Paper";
import {Theme} from "@material-ui/core/es";
import {Field, Form, Formik, FormikActions} from "formik";
import Grid from '@material-ui/core/Grid';

import * as Yup from 'yup';
import {TextField} from "formik-material-ui";
import {Redirect} from "react-router";

const styles = (theme: Theme) => createStyles({
    centered: {
        paddingTop: '20px',
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
    }
});


export interface LoginProps extends WithStyles<typeof styles> {
    login: (username: string, password: string) => Promise<{}>
    isLoggedIn: boolean
}

interface LoginFormikProps {
    login: string,
    password: string
}

const LoginSchema = Yup.object().shape({
    login: Yup.string()
        .min(2, 'Too Short!')
        .max(50, 'Too Long!')
        .required('Required'),
    password: Yup.string()
        .min(2, 'Too Short!')
        .max(50, 'Too Long!')
        .required('Required'),
});


const Login = (props: LoginProps) => {
    const {classes, login, isLoggedIn} = props;

    return <Paper className={classes.centered}>
        {isLoggedIn && <Redirect to="/"/>}
        <Typography variant="h4">Sign in</Typography>
        <Formik
            initialValues={{
                login: '',
                password: '',
            }}
            validationSchema={LoginSchema}
            onSubmit={(values: LoginFormikProps, actions: FormikActions<LoginFormikProps>) => {
                login(values.login, values.password)
                    .then(() => {
                        actions.setSubmitting(false);
                    });
            }}
        >
            {({isSubmitting}) => (
                <Form>
                    <Field className={classes.formField} variant="outlined" type="text" name="login" label="Login"
                           component={TextField}/>
                    <Field className={classes.formField} variant="outlined" type="text" name="password" label="Password"
                           component={TextField}/>

                    <Grid className={classes.loginButtonsGrid} justify="space-between" alignItems="center" container>
                        <Grid item>
                            <Button color="primary" id="foo">
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

export default withStyles(styles, {
    withTheme: true
})(Login)