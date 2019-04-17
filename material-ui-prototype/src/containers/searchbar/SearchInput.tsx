import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import {connect} from "react-redux";
import {AppState} from "../../AppState";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/es/Checkbox";
import QueryInput from "../../components/searchbar/QueryInput";
import {SearchQuery} from "../../entities/SearchQuery";
import {startSearchingAction, toggleUseConstrainsAction} from "../../actions/QueryActions";
import * as H from "history";
import ConstraintsInput from "../../components/searchbar/ConstraintsInput";

const styles = createStyles({});


export interface SearchInputProps extends WithStyles<typeof styles> {
    lastQuery: SearchQuery;
    showConstraints: boolean;
    startSearching: (query: SearchQuery, history?: H.History) => void;
    toggleConstraints: () => void;
    history?: H.History;
    className?: string
}

const SearchInput = (props: SearchInputProps) => {
    const {className = '', lastQuery, showConstraints, toggleConstraints, history, startSearching: parentStartSearching} = props;

    const [query, setQuery] = useState<string>(lastQuery.query);
    const [constraints, setContraints] = useState<string>(lastQuery.constraints);

    const startSearching = () => {
        if (query.length > 0) {
            parentStartSearching({
                query: query,
                constraints: showConstraints ? constraints : ''
            }, history);
        }
    };


    return <div className={className}>
        <QueryInput query={query} setQuery={setQuery} startSearching={startSearching}/>
        <FormControlLabel
            control={<Checkbox
                checked={showConstraints}
                onChange={() => toggleConstraints()}
                value="Use constraints"
            />}
            label="Use constraints"
        />
        {showConstraints &&
        <ConstraintsInput constraints={constraints} setConstraints={setContraints} startSearching={startSearching}/>}
    </div>
};

const mapStateToProps = (state: AppState) => ({
    lastQuery: state.query.lastQuery,
    showConstraints: state.query.useConstraints
});

const mapPropsToDispatch = {
    startSearching: startSearchingAction,
    toggleConstraints: toggleUseConstrainsAction
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapPropsToDispatch)(SearchInput))