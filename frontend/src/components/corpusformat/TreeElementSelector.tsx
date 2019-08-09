import CheckboxTree, {Node} from "react-checkbox-tree";
import React, {useState} from "react";
import {
    CheckBox,
    CheckBoxOutlineBlank,
    IndeterminateCheckBox,
    KeyboardArrowRight,
    KeyboardArrowUp
} from "@material-ui/icons";

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

        const entityNode = allElements.find(elem => elem.value == changedElement);
        if (!entityNode) {
            console.warn(`could not find corresponding information for ${changedElement}`);
            return newSelectedElements;
        }

        if (isChecked) {
            const attributesToSelect = (entityNode.children || [] as Array<Node>)
                .map(node => node.value)
                .filter(name => newSelectedElements.indexOf(name) == -1);
            return [...newSelectedElements, ...attributesToSelect];
        } else {
            return newSelectedElements.filter(elem => elem.indexOf(entityNode.value) == -1);
        }
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
                expandClose: <KeyboardArrowRight/>,
                expandOpen: <KeyboardArrowUp/>,
                expandAll: <span/>,
                collapseAll: <span/>,
                parentClose: <span/>,
                parentOpen: <span/>,
                leaf: <span/>
            }}/>
    </div>
};

export default TreeElementSelector;