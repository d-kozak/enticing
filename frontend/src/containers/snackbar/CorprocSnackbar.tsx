import React from 'react';
import {Snackbar} from "@material-ui/core";
import IconButton from "@material-ui/core/es/IconButton";
import {Theme, WithStyles} from "@material-ui/core/es";
import CloseIcon from '@material-ui/icons/Close';
import withStyles from "@material-ui/core/es/styles/withStyles";
import {connect} from "react-redux";
import {AppState} from "../../AppState";
import {closeSnackBar} from "../../actions/SnackBarActions";

const styles = (theme: Theme) => ({
    close: {
        padding: theme.spacing.unit / 2,
    },
});

export interface CorprocSnackBarProps extends WithStyles<typeof styles> {
    isOpen: boolean,
    setClosed: () => void,
    message: string
}

const CorprocSnackbar = (props: CorprocSnackBarProps) => {
    const {isOpen, setClosed, message, classes} = props;

    const handleClose = (event: React.SyntheticEvent<any>, reason: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setClosed();
    };

    return <Snackbar
        anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
        }}
        open={isOpen}
        autoHideDuration={3000}
        onClose={handleClose}
        ContentProps={{
            'aria-describedby': 'message-id',
        }}
        message={<span id="message-id">{message}</span>}
        action={[
            <IconButton
                key="close"
                aria-label="Close"
                color="inherit"
                className={classes.close}
                onClick={() => setClosed()}
            >
                <CloseIcon/>
            </IconButton>,
        ]}
    />
};


const mapStateToProps = (state: AppState) => {
    const {isOpen, message} = state.snackBar
    return {
        isOpen,
        message
    }
}

const mapDispatchToProps = {
    setClosed: closeSnackBar
}

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(CorprocSnackbar));