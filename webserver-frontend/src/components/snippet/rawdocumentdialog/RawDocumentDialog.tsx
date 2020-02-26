import createStyles from "@material-ui/core/es/styles/createStyles";
import {DialogContent, Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {connect} from "react-redux";
import React from 'react';
import {ApplicationState} from "../../../ApplicationState";
import Dialog from "@material-ui/core/es/Dialog";
import MuiDialogTitle from "@material-ui/core/DialogTitle/DialogTitle";
import Typography from "@material-ui/core/es/Typography";
import IconButton from "@material-ui/core/es/IconButton";
import CloseIcon from '@material-ui/icons/Close';
import {closeRawDocumentDialog} from "../../../reducers/dialog/RawDocumentDialogReducer";
import DocumentDebugInfo from "../DocumentDebugInfoComponent";
import {isDebugMode} from "../../../reducers/selectors";

const styles = (theme: Theme) => createStyles({
    title: {
        borderBottom: `1px solid ${theme.palette.divider}`,
        margin: 0,
        padding: theme.spacing.unit * 2,
    },
    closeButton: {
        position: 'absolute',
        right: theme.spacing.unit,
        top: theme.spacing.unit,
        color: theme.palette.grey[500],
    },
    root: {},
    progress: {}
});

type RawDocumentDialogProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {}

const RawDocumentDialog = (props: RawDocumentDialogProps) => {
    const {info, debug, closeDialog, classes} = props;
    return <Dialog
        open={info != null}
        onClose={closeDialog}
        fullScreen={true}
    >
        {info !== null &&
        <div className={classes.root}>
            <MuiDialogTitle disableTypography className={classes.title}>
                <Typography variant="h4"><a href={info.url}>{info.title}</a></Typography>
                <IconButton aria-label="Close" className={classes.closeButton} onClick={closeDialog}>
                    <CloseIcon/>
                </IconButton>
            </MuiDialogTitle>
            <DialogContent>
                {debug && <DocumentDebugInfo document={info}/>}
                <RawDocumentContent content={info.content} location={info.location} size={info.size}/>
            </DialogContent>
        </div>
        }
    </Dialog>
};

const RawDocumentContent = (props: { content: string, location: number, size: number }) => {
    const {content, location, size} = props;
    const lines = content.split('\n');

    const prefix = lines.slice(0, location).join('\n');
    const middle = lines.slice(location, location + size).join('\n');
    const suffix = lines.splice(location + size).join('\n');

    return <div>
        <pre>
            {prefix}
        </pre>
        <pre style={{color: 'red', textAlign: 'center'}}>

            MATCH START

        </pre>

        <pre>
            {middle}
        </pre>
        <pre style={{color: 'red', textAlign: 'center'}}>

            MATCH END

        </pre>
        <pre>
            {suffix}
        </pre>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    debug: isDebugMode(state),
    info: state.dialog.rawDocumentDialog.info
});
const mapDispatchToProps = {
    closeDialog: closeRawDocumentDialog as () => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(RawDocumentDialog));