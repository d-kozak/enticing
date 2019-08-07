import {Button, createStyles, Grid, WithStyles, withStyles} from "@material-ui/core";
import {ApplicationState} from "../../reducers/ApplicationState";
import {connect} from "react-redux";
import * as React from "react";
import {useState} from "react";
import {SearchSettings} from "../../entities/SearchSettings";
import CheckboxTree, {Node} from 'react-checkbox-tree';
import {
    CheckBox,
    CheckBoxOutlineBlank,
    IndeterminateCheckBox,
    KeyboardArrowDown,
    KeyboardArrowUp
} from "@material-ui/icons";

import 'react-checkbox-tree/lib/react-checkbox-tree.css';
import Typography from "@material-ui/core/es/Typography";

const styles = createStyles({});


interface TreeElementSelectorProps {
    allElements: Array<Node>,
    selectedElements: Array<string>,
    setSelectedElements: (newIndexes: Array<string>) => void
}

function findWhatChanged(newSelectedElements: Array<string>, previouslySelectedElements: Array<string>, nodes: Array<Node>): [string | null, boolean] {
    if (newSelectedElements.length > previouslySelectedElements.length) {
        const newElem = newSelectedElements.find(elem => previouslySelectedElements.indexOf(elem) == -1);
        if (newElem) {
            return [newElem, true]
        } else {
            console.warn("could not find the added element...");
            return [null, false];
        }
    } else if (newSelectedElements.length < previouslySelectedElements.length) {
        const removedElem = previouslySelectedElements.find(elem => newSelectedElements.indexOf(elem) == -1);
        if (removedElem) {
            return [removedElem, false]
        } else {
            console.warn("could not find the removed element...");
            return [null, false];
        }
    } else {
        console.warn("same amount of new and previously selected elements, cannot determine what changed...");
        return [null, false];
    }

}

const TreeElementSelector = (props: TreeElementSelectorProps) => {
    const {allElements, selectedElements, setSelectedElements} = props;

    const [expanded, setExpanded] = useState([] as Array<string>);

    const updateChildren = (newSelectedElements: Array<string>): Array<string> => {
        const [changedElement, isChecked] = findWhatChanged(newSelectedElements, selectedElements, allElements);
        if (changedElement == null) return newSelectedElements;
        const isAttribute = changedElement.indexOf("/") != -1;
        if (isAttribute) {
            if (isChecked) {
                const entityName = changedElement.split("/")[0];
                return newSelectedElements.indexOf(entityName) === -1 ? [...newSelectedElements, entityName] : newSelectedElements;
            } else {
                return newSelectedElements;
            }
        }

        if (isChecked) {
            const entityNode = allElements.find(elem => elem.value == changedElement);
            if (entityNode) {
                const attributesToSelect = (entityNode.children || [] as Array<Node>)
                    .map(node => node.value)
                    .filter(name => newSelectedElements.indexOf(name) == -1);
                return [...newSelectedElements, ...attributesToSelect];
            } else {
                console.warn(`could not find corresponding information for ${changedElement}`)
            }
        } else {
            const entityNode = allElements.find(elem => elem.value == changedElement);
            if (entityNode) {
                return newSelectedElements.filter(elem => elem.indexOf(entityNode.value) == -1);
            } else {
                console.warn(`could not find corresponding information for ${changedElement}`)
            }
        }

        return newSelectedElements;
    };

    return <div>
        <CheckboxTree
            checked={selectedElements}
            expanded={expanded}
            nodes={allElements}
            showNodeIcon={false}
            noCascade={true}
            onCheck={checked => setSelectedElements(updateChildren(checked))}
            onExpand={expanded => setExpanded(expanded)}
            icons={{
                check: <CheckBox color="primary"/>,
                uncheck: <CheckBoxOutlineBlank color="primary"/>,
                halfCheck: <IndeterminateCheckBox color="primary"/>,
                expandClose: <KeyboardArrowDown/>,
                expandOpen: <KeyboardArrowUp/>,
                expandAll: <span/>,
                collapseAll: <span/>,
                parentClose: <span/>,
                parentOpen: <span/>,
                leaf: <span/>
            }}/>
    </div>
};

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