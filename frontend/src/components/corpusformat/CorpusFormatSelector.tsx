import createStyles from "@material-ui/core/es/styles/createStyles";
import {Grid, Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {connect} from "react-redux";
import React, {useState} from 'react';
import {Node} from "react-checkbox-tree";
import Typography from "@material-ui/core/es/Typography";
import TreeElementSelector from "./TreeElementSelector";
import Button from "@material-ui/core/Button";
import {SearchSettings} from "../../entities/SearchSettings";
import {CorpusFormat} from "../../entities/CorpusFormat";
import {SelectedMetadata} from "../../entities/SelectedMetadata";
import {ApplicationState} from "../../ApplicationState";
import {createMetadata, splitMetadata} from "./metadataTransformation";
import {saveSelectedMetadataRequest} from "../../reducers/UserReducer";


const styles = (theme: Theme) => createStyles({});

type CorpusFormatSelectorProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {
    searchSettings: SearchSettings,
    corpusFormat: CorpusFormat,
    selectedMetadata: SelectedMetadata
}



const CorpusFormatSelector = (props: CorpusFormatSelectorProps) => {
    const {searchSettings, corpusFormat, saveCorpusFormat, selectedMetadata} = props;

    const [indexes, attributes] = splitMetadata(selectedMetadata);

    const [selectedIndexes, setSelectedIndexes] = useState(indexes);
    const [selectedAttributes, setSelectedAttributes] = useState(attributes);


    const indexNodes = Object.keys(corpusFormat.indexes)
        .map(name => ({
            value: name,
            label: <span>{name} : {corpusFormat.indexes[name]}</span>
        } as Node));

    const entityNodes = Object.keys(corpusFormat.entities)
        .map(entityName => ({
            value: entityName,
            label: <span>{entityName} : {corpusFormat.entities[entityName].description}</span>,
            children: Object.keys(corpusFormat.entities[entityName].attributes).map(attributeName => ({
                value: `${entityName}/${attributeName}`,
                label: <span>{attributeName} : {corpusFormat.entities[entityName].attributes[attributeName]}</span>
            }))
        } as Node));
    return <div>
        <Typography variant="h5">Select wanted metadata</Typography>
        <Grid container justify="flex-start">
            <Grid item>
                <Typography variant="h5">Indexes</Typography>
                <TreeElementSelector allElements={indexNodes} selectedElements={selectedIndexes}
                                     setSelectedElements={setSelectedIndexes}/>
            </Grid>
            <Grid item>
                <Typography variant="h5">Entities</Typography>
                <TreeElementSelector allElements={entityNodes} selectedElements={selectedAttributes}
                                     setSelectedElements={setSelectedAttributes}/>
            </Grid>
        </Grid>
        <Button
            onClick={() => saveCorpusFormat(createMetadata(selectedIndexes, selectedAttributes), searchSettings.id)}>Save</Button>
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {
    saveCorpusFormat: saveSelectedMetadataRequest as (metadata: SelectedMetadata, settingsId: string) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(CorpusFormatSelector));