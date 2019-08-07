import {Button, createStyles, Grid, WithStyles, withStyles} from "@material-ui/core";
import {ApplicationState} from "../../reducers/ApplicationState";
import {connect} from "react-redux";
import * as React from "react";
import {useState} from "react";
import {SearchSettings} from "../../entities/SearchSettings";
import {Node} from 'react-checkbox-tree';


import 'react-checkbox-tree/lib/react-checkbox-tree.css';
import Typography from "@material-ui/core/es/Typography";
import TreeElementSelector from "./TreeElementSelector";

const styles = createStyles({});



export type CorpusFormatSelectorProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    & {
    searchSettings: SearchSettings
}

const CorpusFormatSelector = (props: CorpusFormatSelectorProps) => {
    const {searchSettings} = props;
    if (!searchSettings.corpusFormat) {
        return <span>no corpus format</span>
    }
    const corpusFormat = searchSettings.corpusFormat;

    const [selectedIndexes, setSelectedIndexes] = useState(Object.keys(corpusFormat.indexes));
    const [selectedAttributes, setSelectedAttributes] = useState([...Object.keys(corpusFormat.entities)])


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


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(CorpusFormatSelector))