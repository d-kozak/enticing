import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import {withStyles} from '@material-ui/core/styles';

import Button from '@material-ui/core/Button';

import React from 'react';
import Paper from "@material-ui/core/es/Paper";
import {Theme} from "@material-ui/core/es";
import {Field, Form, Formik, FormikActions} from "formik";

import * as Yup from 'yup';
import {TextField} from "formik-material-ui";

const styles = (theme: Theme) => createStyles({
    centered: {
        position: 'fixed',
        top: '40%',
        left: '50%',
        transform: 'translate(-50%, -50%)'
    },
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
    }
});


export interface LoginProps extends WithStyles<typeof styles> {

}

interface FormProps {
    login: string,
    password: string
}

const schema = Yup.object({
    login: Yup.string().required(''),
    password: Yup.string().required('')
})

const Login = (props: LoginProps) => {
    const {classes} = props;

    return <Paper className={classes.centered}>
        <Formik
            initialValues={{
                login: '',
                password: ''
            }}
            validationSchema={schema}
            onSubmit={(values: FormProps, actions: FormikActions<FormProps>) => {
                console.log(values);
                console.log(schema.validate(values));
                actions.setSubmitting(false);
            }}
        >
            {({isSubmitting}: any) => (
                <Form>
                    <Field variant="outlined" type="text" name="login" label="Login" component={TextField}/>
                    <Field variant="outlined" type="password" name="password" label="Password" component={TextField}/>
                    <Button type="submit" disabled={isSubmitting}>
                        Submit
                    </Button>
                </Form>
            )}
        </Formik>
    </Paper>
};

export default withStyles(styles, {
    withTheme: true
})(Login)