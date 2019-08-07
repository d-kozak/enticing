import {createStyles, WithStyles, withStyles} from "@material-ui/core";
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
import {CorpusFormat} from "../../entities/CorpusFormat";


const styles = createStyles({});

export type CorpusFormatSelectorProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    & {
    searchSettings: SearchSettings
}


const createNodes = (corpusFormat: CorpusFormat): Array<Node> => {
    return [];
}


const CorpusFormatSelector = (props: CorpusFormatSelectorProps) => {
    const {searchSettings} = props;
    if (!searchSettings.corpusFormat) {
        return <span>no corpus format</span>
    }
    const [checked, setChecked] = useState([] as Array<string>);
    const [expanded, setExpanded] = useState([] as Array<string>);

    const nodes = createNodes(searchSettings.corpusFormat);

    return <CheckboxTree
        checked={checked}
        expanded={expanded}
        nodes={nodes}
        onCheck={setChecked}
        onExpand={setExpanded}
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
        }}
    />
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(CorpusFormatSelector))