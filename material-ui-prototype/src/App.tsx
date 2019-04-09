import React, {useState} from 'react';
import './App.css';
import MenuAppBar from "./CorprocAppBar";
import {CssBaseline} from "@material-ui/core";
import CorprocSnackBar from "./CorprocSnackbar";
import SearchBar from "./CenteredSearchBar";


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

    const startSearching = (query: string) => {
        setSnackbarState({
            isOpen: true,
            message: `Quering '${query}'`
        })
    };

    return <div>
        <CssBaseline/>
        <MenuAppBar
            isLoggedIn={isLoggedId}
            setLoggedIn={handleLogin}/>
        <SearchBar startSearching={startSearching}/>
        <CorprocSnackBar isOpen={snackbarState.isOpen}
                         setClosed={() => setSnackbarState({...snackbarState, isOpen: false})}
                         message={snackbarState.message}
        />
    </div>
};


export default App;
