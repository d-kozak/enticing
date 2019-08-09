import {createStyles, Grid, WithStyles, withStyles} from "@material-ui/core";
import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import * as React from "react";
import {useEffect, useState} from "react";
import {SearchSettings} from "../../entities/SearchSettings";
import {Node} from 'react-checkbox-tree';

import Button from '@material-ui/core/Button';

import 'react-checkbox-tree/lib/react-checkbox-tree.css';
import Typography from "@material-ui/core/es/Typography";
import TreeElementSelector from "./TreeElementSelector";
import {loadCorpusFormatRequest} from "../../reducers/SearchSettingsReducer";
import {getUser, isLoggedIn, loadSelectedMetadataRequest} from "../../reducers/UserReducer";
import {SelectedMetadata} from "../../entities/SelectedMetadata";

const styles = createStyles({});


export type CorpusFormatSelectorProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    & {
    searchSettings: SearchSettings
}


const splitMetadata = (metadata: SelectedMetadata | undefined): [Array<string>, Array<string>] => {
    if (!metadata) return [[], []];

    const attributes = [] as Array<string>;

    for (let entityName of Object.keys(metadata.entities)) {
        attributes.push(entityName);
        for (let attribute of metadata.entities[entityName].attributes) {
            attributes.push(`${entityName}/${attribute}`);
        }
    }

    return [metadata.indexes, attributes];
};

const CorpusFormatSelector = (props: CorpusFormatSelectorProps) => {
    const {searchSettings, isLoggedIn, user, loadCorpusFormat, loadSelectedMetadata} = props;

    if (!searchSettings.corpusFormat || (isLoggedIn && !user.selectedMetadata[searchSettings.id])) {
        return <div>
            <Typography variant="body1">Loading...</Typography>
            <Button onClick={() => loadCorpusFormat(searchSettings)}>Try again</Button>
        </div>
    }

    useEffect(() => {
        if (!searchSettings.corpusFormat) {
            loadCorpusFormat(searchSettings);
        }
    }, []);


    useEffect(() => {
        if (isLoggedIn && !user.selectedMetadata[searchSettings.id]) {
            loadSelectedMetadata(searchSettings.id);
        }
    }, []);


    const corpusFormat = searchSettings.corpusFormat;

    const [indexes, attributes] = splitMetadata(user.selectedMetadata[searchSettings.id]);

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
        <Button>Save</Button>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    isLoggedIn: isLoggedIn(state),
    user: getUser(state)
});

const mapDispatchToProps = {
    loadCorpusFormat: loadCorpusFormatRequest as (settings: SearchSettings) => void,
    loadSelectedMetadata: loadSelectedMetadataRequest as (settingsId: string) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(CorpusFormatSelector))