import React, {useState} from 'react';
import './App.css';
import MenuAppBar from "./CorprocAppBar";
import {CssBaseline} from "@material-ui/core";
import CorprocSnackBar from "./CorprocSnackbar";
import SearchBar from "./CenteredSearchBar";
import LinearProgress from "@material-ui/core/es/LinearProgress";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Login from "./Login";
import Unknown from "./Unknown";
import {Field, Form, Formik} from "formik";
import * as Yup from 'yup';
import {TextField} from "formik-material-ui";


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


const FormikTest = () => {

    return <div>
        <h1>Testing formik</h1>
        <Formik
            initialValues={{
                login: '',
                password: '',
            }}
            validationSchema={LoginSchema}
            onSubmit={values => {
                // same shape as initial values
                console.log(values);
            }}
        >
            {({isSubmitting}) => (
                <Form>
                    <Field variant="outlined" type="text" name="login" label="Login" component={TextField}/>
                    <Field variant="outlined" type="text" name="password" label="Password" component={TextField}/>

                    <button type="submit" disabled={isSubmitting}>Submit</button>
                </Form>
            )}
        </Formik>
    </div>
};


const App = () => {
    const [isLoggedId, setLoggedIn] = useState(false);

    const [snackbarState, setSnackbarState] = useState({
        isOpen: false,
        message: ''
    });

    const [showProgress, setShowProgress] = useState(false);

    const handleLogin = (isLoggedIn: boolean) => {
        setLoggedIn(isLoggedIn);
        setSnackbarState({
            isOpen: true,
            message: isLoggedIn ? 'Logged in' : 'Logged out'
        });
    }

    const startSearching = (query: string) => {
        setSnackbarState({
            isOpen: true,
            message: `Quering '${query}'`
        });
        setShowProgress(true);
        setTimeout(() => {
            setShowProgress(false);
        }, 3000);
    };

    return <div>
        <CssBaseline/>
        <Router>
            <MenuAppBar
                isLoggedIn={isLoggedId}
                setLoggedIn={handleLogin}/>
            {showProgress && <LinearProgress color="secondary"/>}

            <Switch>

                <Route path="/" component={FormikTest}/>

                <Route path="/" exact component={() => <SearchBar startSearching={startSearching}/>}/>
                <Route path="/login" component={Login}/>
                <Route component={Unknown}/>
            </Switch>
        </Router>
        <CorprocSnackBar isOpen={snackbarState.isOpen}
                         setClosed={() => setSnackbarState({...snackbarState, isOpen: false})}
                         message={snackbarState.message}
        />
    </div>
};


export default App;
