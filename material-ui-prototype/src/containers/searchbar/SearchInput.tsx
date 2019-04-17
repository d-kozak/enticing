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

const styles = createStyles({});


export interface SearchInputProps extends WithStyles<typeof styles> {
    lastQuery: SearchQuery;
    showConstraints: boolean;
    startSearching: (query: SearchQuery, history?: H.History) => void;
    toggleConstraints: () => void;
    history?: H.History;
}

const SearchInput = (props: SearchInputProps) => {
    const {lastQuery, showConstraints, toggleConstraints, history, startSearching} = props;

    const [query, setQuery] = useState<string>(lastQuery.query);
    const [constraints, setContraints] = useState<string>(lastQuery.constraints);

    const queryStartSearching = (query: string) => {
        if (query.length > 0) {
            startSearching({
                query: query,
                constraints: constraints
            }, history);
        }
    };

    return <React.Fragment>
        <QueryInput query={query} setQuery={setQuery} startSearching={queryStartSearching}/>
        <FormControlLabel
            control={<Checkbox
                checked={showConstraints}
                onChange={() => toggleConstraints()}
                value="Use constraints"
            />}
            label="Use constraints"
        />
        {showConstraints && <div>
            <h1>Constraints</h1>
        </div>}
    </React.Fragment>
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