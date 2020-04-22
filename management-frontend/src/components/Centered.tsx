import React, {FunctionComponent} from "react";
import {makeStyles} from "@material-ui/core/styles";


const useStyles = makeStyles({
    root: {
        height: '85vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center'
    }
});


export const Centered: FunctionComponent = (props) => {
    const {children} = props;
    const {root} = useStyles();
    return <div className={root}>
        {children}
    </div>
}