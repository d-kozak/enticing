import React, {useState} from 'react';
import './App.css';
import MenuAppBar from "./MenuAppBar";
import {CssBaseline} from "@material-ui/core";
import CorprocSnackBar from "./SimpleSnackbar";


const App = () => {
    const [isLoggedId, setLoggedIn] = useState(false);

    const [snackbarState, setSnackbarState] = useState({
        isOpen: false,
        message: ''
    });


    const handleLogin = (isLoggedIn: boolean) => {
        setLoggedIn(isLoggedIn);
        setSnackbarState({
            isOpen: true,
            message: isLoggedIn ? 'Logged in' : 'Logged out'
        });
    }

    return <div>
        <CssBaseline/>
        <MenuAppBar
            showTitle={false}
            isLoggedIn={isLoggedId}
            setLoggedIn={handleLogin}/>
        <CorprocSnackBar isOpen={snackbarState.isOpen}
                         setClosed={() => setSnackbarState({...snackbarState, isOpen: false})}
                         message={snackbarState.message}
        />
    </div>
};


export default App;
