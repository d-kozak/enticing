import * as Yup from 'yup';
import React, {useCallback, useState} from 'react';
import Button from '@material-ui/core/Button';
import {TextField} from 'formik-material-ui';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {ApplicationState} from "../../../ApplicationState";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {connect} from "react-redux";
import {Field, Form, Formik} from "formik";
import {
    Checkbox,
    FormControl,
    Input,
    InputLabel,
    LinearProgress,
    ListItemText,
    MenuItem,
    Select
} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import {postRequest, putRequest} from "../../../network/requests";
import {useInterval} from "../../../utils/useInterval";
import {requestAllComponents} from "../../../reducers/componentsReducer";
import {clearComponentsFromCorpus} from "../../../reducers/corpusesReducer";
import {Corpus} from "../../../entities/Corpus";

type AddOrEditCorpusDialogProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    editedCorpus?: Corpus
}

const useStyles = makeStyles({
    formField: {
        display: 'block',
        margin: '10px 30px'
    },
    root: {
        padding: '10px'
    }
})

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};


const AddNewCorpusSchema = Yup.object().shape({
    'name': Yup.string()
        .min(4)
        .required('Name is required'),
});

const AddOrEditCorpusDialog = (props: AddOrEditCorpusDialogProps) => {
    const {requestAllComponents, clearComponentsFromCorpus, components, editedCorpus, openSnackbarAction} = props;
    const [open, setOpen] = useState(false);
    const [progress, setProgress] = useState(false);

    const addingNew = editedCorpus === undefined;

    const classes = useStyles();

    const previouslySelectedComponents = Object.values(editedCorpus?.components?.elements || {})
        .map(it => it.id);

    const [selectedComponents, setSelectedComponents] = useState<Array<string>>(previouslySelectedComponents)

    const allSelected = selectedComponents.length === components.totalElements;

    const updateComponents = useCallback(() => {
        if (open)
            requestAllComponents();
    }, [open, requestAllComponents])

    useInterval(updateComponents, 1_000);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleGlobalSelection = () => {
        if (allSelected) setSelectedComponents([])
        else setSelectedComponents(Object.values(components.elements).map(component => component.id))
    };

    return (
        <div>
            <Button color="primary" variant="contained" onClick={handleClickOpen}>
                {addingNew ? "Add new corpus" : "Edit corpus"}
            </Button>
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" className={classes.root}>
                <DialogTitle
                    id="form-dialog-title">{addingNew ? "Add new corpus" : `Editing corpus ${editedCorpus?.name}`}</DialogTitle>
                {progress && <LinearProgress/>}
                <DialogContent>
                    <Formik
                        initialValues={{name: editedCorpus?.name || ''}}
                        validationSchema={AddNewCorpusSchema}
                        onSubmit={({name}, actions) => {
                            setProgress(true)
                            const req = {
                                id: editedCorpus?.id || "0",
                                components: selectedComponents,
                                name
                            }
                            const request = addingNew ? postRequest : putRequest;
                            request<Corpus>("/corpus", req)
                                .then(corpus => {
                                    setProgress(false)
                                    setOpen(false)
                                    actions.setSubmitting(false)
                                    openSnackbarAction(`Corpus ${name} created`);
                                    if (!addingNew) {
                                        clearComponentsFromCorpus(corpus.id);
                                    }
                                })
                                .catch(err => {
                                    setProgress(false)
                                    console.error(err)
                                    actions.setSubmitting(false)
                                })
                        }}
                    >
                        {({isSubmitting}) => <Form>

                            <Field variant="outlined" type="string" name="name" label="Name"
                                   className={classes.formField}
                                   component={TextField}/>

                            <FormControl className={classes.formField}>
                                <InputLabel id="corpus-components-label">Components</InputLabel>
                                <Select
                                    labelId="corpus-components-label"
                                    multiple
                                    value={selectedComponents}
                                    onChange={(event) => setSelectedComponents(event.target.value as Array<string>)}
                                    input={<Input/>}
                                    renderValue={(selected) => (selected as Array<string>).length + " components"}
                                    MenuProps={MenuProps}
                                >
                                    {Object.values(components.elements).map((component) => (
                                        <MenuItem key={component.id} value={component.id}>
                                            <Checkbox checked={selectedComponents.indexOf(component.id) > -1}/>
                                            <ListItemText primary={component.serverAddress}/>
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>

                            <FormControl className={classes.formField}>
                                <Button color="secondary"
                                        onClick={handleGlobalSelection}>{allSelected ? "Clear all" : "Select all"}</Button>
                            </FormControl>

                            <DialogActions>
                                <Button onClick={handleClose} color="primary">
                                    Cancel
                                </Button>
                                <Button disabled={isSubmitting} type="submit" color="primary" variant="contained">
                                    {addingNew ? "Add" : "Edit"}
                                </Button>
                            </DialogActions>
                        </Form>}
                    </Formik>
                </DialogContent>
            </Dialog>
        </div>
    );
}

const mapStateToProps = (state: ApplicationState) => ({
    components: state.components
});

const mapDispatchToProps = {
    openSnackbarAction,
    clearComponentsFromCorpus: clearComponentsFromCorpus as (corpusId: string) => void,
    requestAllComponents: requestAllComponents as () => void
};

export default connect(mapStateToProps, mapDispatchToProps)(AddOrEditCorpusDialog);