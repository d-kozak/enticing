import React, {useState} from 'react';
import {Button, createStyles, Dialog, DialogActions, LinearProgress, withStyles, WithStyles} from "@material-ui/core";
import {Field, Form, Formik} from "formik";
import * as Yup from "yup";
import {TextField} from "formik-material-ui";
import {connect} from "react-redux";
import {CommandDto, CommandRequest} from "../../../entities/CommandDto";
import {postRequest} from "../../../network/requests";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {ApplicationState} from "../../../ApplicationState";
import {useHistory} from "react-router";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";

const styles = createStyles({
    mainElement: {
        minWidth: '275px',
        paddingTop: '0px',
        paddingBottom: '10px',
        position: 'fixed',
        top: '40%',
        left: '50%',
        transform: 'translate(-50%, -25%)',
        textAlign: 'center'
    }, formField: {
        display: 'block',
        margin: '0px 30px'
    }, progress: {
        marginBottom: '15px'
    },
    '@media (min-height:800px)': {
        mainElement: {
            transform: 'translate(-50%, -50%)'
        }, formField: {
            margin: '10px 30px'
        }
    }
});


export type RequestNewBuildDialogProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

interface RequestNewBuildFormikValues {
    buildId: string
}

const minLenText = 'At least 5 characters, please';
const maxLenText = 'Max 64 characters, please';

const RequestNewBuildDialogSchema = Yup.object({
    buildId: Yup.string()
        .required('Please specify a unique build id')
        .min(5, minLenText)
        .max(64, maxLenText),
})

const RequestNewBuildDialog = (props: RequestNewBuildDialogProps) => {
    const {classes} = props;

    const [open, setOpen] = useState(false);
    const [showProgress, setShowProgress] = useState(false);

    const history = useHistory();

    const initialValues: RequestNewBuildFormikValues = {
        buildId: ''
    };

    return <React.Fragment>
        <Button variant="contained" color="primary" onClick={() => setOpen(true)}>
            Request new build
        </Button>
        <Dialog
            open={open}
            onClose={() => setOpen(false)}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description">
            {showProgress && <LinearProgress className={classes.progress}/>}

            <DialogTitle id="alert-dialog-title">Request new build</DialogTitle>
            <DialogContent>
                <Formik
                    initialValues={initialValues}
                    validationSchema={RequestNewBuildDialogSchema}
                    onSubmit={({buildId}, actions) => {
                        setShowProgress(true);
                        const req: CommandRequest = {
                            type: "BUILD",
                            arguments: buildId
                        }
                        postRequest<CommandDto>("/command", req)
                            .then(command => {
                                history.push(`/command/${command.id}`)
                                openSnackbarAction("New build requested")
                            }).catch(err => {
                            console.error(err);
                            openSnackbarAction("Failed to request new build")
                        }).finally(() => {
                            setShowProgress(false)
                            setOpen(false)
                        })
                    }}
                >
                    {({isSubmitting}) => <Form>
                        <Field className={classes.formField} variant="outlined" type="text" name="buildId"
                               label="BuildId"
                               component={TextField}/>
                        <DialogActions>
                            <Button onClick={() => setOpen(false)}>Cancel</Button>
                            <Button variant="contained" color="primary" type="submit"
                                    disabled={isSubmitting}>Submit</Button>
                        </DialogActions>
                    </Form>
                    }
                </Formik>
            </DialogContent>
        </Dialog>
    </React.Fragment>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    openSnackbarAction
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(RequestNewBuildDialog));