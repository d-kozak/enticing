import React, {useState} from 'react';
import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";
import Paper from "@material-ui/core/es/Paper";
import {Redirect} from "react-router";
import {Field, Form, Formik} from "formik";
import * as Yup from "yup";
import {TextField} from "formik-material-ui";
import Button from "@material-ui/core/es/Button";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import Typography from "@material-ui/core/Typography";
import {AppState} from "../../reducers/RootReducer";
import {signUpAction} from "../../actions/UserActions";
import {connect} from "react-redux";

const styles = createStyles({
    mainElement: {
        minWidth: '275px',
        paddingTop: '0px',
        paddingBottom: '10px',
        position: 'fixed',
        top: '40%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        textAlign: 'center'
    }, formField: {
        display: 'block',
        margin: '10px 30px'
    }, progress: {
        marginBottom: '15px'
    }
});


export interface SignUpProps extends WithStyles<typeof styles> {
    signUp: (login: string, password: string, onError: (errors: any) => void) => void,
    isLoggedIn: boolean
}


interface SignUpFormikValues {
    login: string,
    password1: string,
    password2: string
}

const minLenText = 'At least 5 characters, please';
const maxLenText = 'Max 32 characters, please';

const SignUpSchema = Yup.object({
    login: Yup.string()
        .required('Please write your login')
        .min(5, minLenText)
        .max(32, maxLenText),
    password1: Yup.string()
        .required('Please write your password')
        .min(5, minLenText)
        .max(32, maxLenText),
    password2: Yup.string()
        .required('Please write your password again')
        .min(5, minLenText)
        .max(32, maxLenText)
        .oneOf([Yup.ref('password1'), null], 'Passwords do not match')
});

const SignUp = (props: SignUpProps) => {
    const {signUp, isLoggedIn, classes} = props;

    const [showProgress, setShowProgress] = useState(false);

    const initialValues: SignUpFormikValues = {
        login: '',
        password1: '',
        password2: ''
    };

    return <Paper className={classes.mainElement} style={{paddingTop: showProgress ? '0px' : '20px'}}>
        {isLoggedIn && <Redirect to="/"/>}
        {showProgress && <LinearProgress className={classes.progress}/>}

        <Typography variant="h4">Sign up</Typography>

        <Formik
            initialValues={initialValues}
            validationSchema={SignUpSchema}
            onSubmit={(values, actions) => {
                const {login, password1: password} = values;
                setShowProgress(true);
                signUp(login, password, errors => {
                    setShowProgress(false);
                    actions.setErrors(errors);
                    actions.setSubmitting(false);
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
                <Button variant="contained" color="primary" type="submit" disabled={isSubmitting}>Submit</Button>
            </Form>

            }
        </Formik>
    </Paper>
};


const mapStateToProps = (state: AppState) => ({
    isLoggedIn: state.user.isLoggedIn
})

const mapDispatchToProps = {
    signUp: signUpAction
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(SignUp));