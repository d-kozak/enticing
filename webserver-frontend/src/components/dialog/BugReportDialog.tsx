import createStyles from "@material-ui/core/es/styles/createStyles";
import {Button, Dialog, DialogActions, DialogContent, DialogTitle, Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import React, {useState} from 'react';
import {SearchResult} from "../../entities/SearchResult";
import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import {BugReport} from "../../entities/BugReport";
import {LinearProgress, TextField} from "@material-ui/core/es";
import {openSnackbar} from "../../reducers/SnackBarReducer";
import axios from "axios";
import {API_BASE_PATH} from "../../globals";
import {consoleDump} from "../utils/dump";


const styles = (theme: Theme) => createStyles({});

type BugReportDialogDialogProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & WithStyles<typeof styles> & {
    searchResult: SearchResult,
    open: boolean
    onSubmit: () => void
    onClose: () => void
}

const BugReportDialog = (props: BugReportDialogDialogProps) => {
    const {query, open, onSubmit, searchResult, openSnackbar, onClose} = props;

    const [description, setDescription] = useState("");
    const [progress, setProgress] = useState(false);

    const clearStateAndClose = () => {
        setDescription("");
        onClose();
    }

    const reportBug = () => {
        const report: BugReport = {
            host: searchResult.host,
            collection: searchResult.collection,
            documentId: searchResult.documentId,
            matchInterval: {
                from: searchResult.payload.location,
                to: searchResult.payload.location + searchResult.payload.size - 1
            },
            query,
            description
        };

        setProgress(true);
        axios.post(`${API_BASE_PATH}/bug-report`, report, {
            withCredentials: true
        }).then(() => {
            openSnackbar("Bug submitted successfully")
            setDescription("");
            onSubmit();
        }).catch(err => {
            openSnackbar("Failed to submit the bug, please try again")
            consoleDump(err);
        }).finally(() => {
            setProgress(false);
        });
    };


    return <Dialog
        open={open}
        onClose={clearStateAndClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
    >
        <DialogTitle id="alert-dialog-title">Bug report</DialogTitle>
        <DialogContent>
            {progress && <LinearProgress/>}
            <TextField
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                label="What is wrong?"
                multiline
                rows={4}
                variant="filled"
            />
        </DialogContent>
        <DialogActions>
            <Button onClick={clearStateAndClose} color="primary">
                Cancel
            </Button>
            <Button onClick={reportBug} color="primary" autoFocus>
                Confirm
            </Button>
        </DialogActions>
    </Dialog>
};


const mapStateToProps = (state: ApplicationState) => ({
    query: state.searchResult.query
});

const mapDispatchToProps = {
    openSnackbar: openSnackbar
};

export default withStyles(styles, {withTheme: true})((connect(mapStateToProps, mapDispatchToProps)(BugReportDialog)));