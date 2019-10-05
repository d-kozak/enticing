import createStyles from "@material-ui/core/es/styles/createStyles";
import {FormControl, Grid, MenuItem, Select, Theme, WithStyles} from "@material-ui/core";
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
import {mapValues} from 'lodash';
import EntityColorPicker, {EntityColorConfig} from "./EntityColorPicker";

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


function getInitialColors(corpusFormat: CorpusFormat, metadata?: SelectedMetadata): EntityColorConfig {
    if (!metadata) return mapValues(corpusFormat.entities, (entity) => "9900EF");
    const config: EntityColorConfig = {};

    for (let entity of Object.keys(corpusFormat.entities)) {
        const entityInfo = metadata.entities[entity];
        config[entity] = entityInfo && entityInfo.color || "9900EF";
    }

    return config;
}

const CorpusFormatSelector = (props: CorpusFormatSelectorProps) => {
    const {searchSettings, corpusFormat, saveCorpusFormat, selectedMetadata} = props;

    const [indexes, attributes] = splitMetadata(selectedMetadata);

    const [selectedIndexes, setSelectedIndexes] = useState(indexes);
    const [selectedAttributes, setSelectedAttributes] = useState(attributes);

    const [selectedDefaultIndex, setSelectedDefaultIndex] = useState(selectedMetadata && selectedMetadata.defaultIndex || "token");
    const [selectedColors, setSelectedColors] = useState(getInitialColors(corpusFormat, selectedMetadata));

    const toggleSelectedIndexes = () => {
        if (selectedIndexes.length == 0) {
            setSelectedIndexes(Object.keys(corpusFormat.indexes))
        } else {
            setSelectedIndexes([])
        }
    };

    const toggleSelectedAttributes = () => {
        if (selectedAttributes.length == 0) {
            const allAttributes = Object.keys(corpusFormat.entities)
                .map(entityName => [entityName, ...Object.keys(corpusFormat.entities[entityName].attributes).map(attribute => entityName + '/' + attribute)])
                .flat();
            setSelectedAttributes(allAttributes);
        } else {
            setSelectedAttributes([])
        }
    };

    const indexNodes = Object.keys(corpusFormat.indexes)
        .map(name => ({
            value: name,
            label: <span>{corpusFormat.indexes[name] && `${name} : ${corpusFormat.indexes[name]}` || name}</span>
        } as Node));

    const entityNodes = Object.keys(corpusFormat.entities)
        .map(entityName => ({
            value: entityName,
            label:
                <span>{corpusFormat.entities[entityName].description && `${entityName} : ${corpusFormat.entities[entityName].description}` || entityName}</span>,
            children: Object.keys(corpusFormat.entities[entityName].attributes).map(attributeName => ({
                value: `${entityName}/${attributeName}`,
                label:
                    <span>{corpusFormat.entities[entityName].attributes[attributeName] && `${attributeName} : ${corpusFormat.entities[entityName].attributes[attributeName]}` || attributeName}</span>
            }))
        } as Node));
    return <div>
        <Grid container justify="flex-start">
            <Grid item>
                <Typography variant="h5">Indexes</Typography>
                <Button
                    onClick={toggleSelectedIndexes}>{selectedIndexes.length === 0 ? "Select all" : "Clear all"}</Button>
                <TreeElementSelector allElements={indexNodes} selectedElements={selectedIndexes}
                                     setSelectedElements={setSelectedIndexes}/>
            </Grid>
            <Grid item>
                <Typography variant="h5">Entities</Typography>
                <Typography variant="h6">Attributes</Typography>
                <Button
                    onClick={toggleSelectedAttributes}>{selectedAttributes.length === 0 ? "Select all" : "Clear all"}</Button>
                <TreeElementSelector allElements={entityNodes} selectedElements={selectedAttributes}
                                     setSelectedElements={setSelectedAttributes}/>
                <Typography variant="h6">Colors</Typography>
                <EntityColorPicker selectedColors={selectedColors} setSelectedColors={setSelectedColors}/>
            </Grid>
            <Grid item>
                <Typography variant="h5">Default index</Typography>
                <FormControl>
                    <Select
                        value={selectedDefaultIndex}
                        onChange={(event) => setSelectedDefaultIndex(event.target.value)}
                        inputProps={{
                            name: 'index',
                            id: 'default-index',
                        }}
                    >
                        {Object.keys(corpusFormat.indexes).map(index => <MenuItem key={index}
                                                                                  value={index}>{index}</MenuItem>)}
                    </Select>
                </FormControl>
            </Grid>
        </Grid>
        <Grid container justify="flex-end">
            <Button
                onClick={() => saveCorpusFormat(createMetadata(selectedIndexes, selectedAttributes, selectedColors, selectedDefaultIndex), searchSettings.id)}>Save
                selected metadata</Button>
        </Grid>
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {
    saveCorpusFormat: saveSelectedMetadataRequest as (metadata: SelectedMetadata, settingsId: string) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(CorpusFormatSelector));