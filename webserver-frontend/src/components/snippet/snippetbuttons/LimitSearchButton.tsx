import React from 'react';

import withStyles from "@material-ui/core/es/styles/withStyles";
import {connect} from "react-redux";
import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import {ApplicationState} from "../../../ApplicationState";
import {startSearchingAction} from "../../../actions/QueryActions";
import {getSelectedSearchSettings} from "../../../reducers/selectors";
import {openSnackbar} from '../../../reducers/SnackBarReducer';
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import RemoveIcon from "@material-ui/icons/Remove";
import {User} from "../../../entities/User";
import {SearchSettings} from "../../../entities/SearchSettings";
import * as H from "history";
import {SearchResult} from "../../../entities/SearchResult";


const styles = createStyles({});

export type LimitSearchButtonProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    & {
    snippet: SearchResult,
    history: H.History
}

const LimitSearchButton = (props: LimitSearchButtonProps) => {
    const {query, snippet, history, user, startSearching, openSnackbar, selectedSettings} = props;

    const onClick = () => {
        if (selectedSettings === null) {
            openSnackbar("no search settings selected");
            return
        }
        const constraint = `document.url:'${snippet.url}'`;
        startSearching(constraint + " " + query, user, selectedSettings, history);
    };

    return <Tooltip
        title='Limit search to this document only'>
        <IconButton onClick={onClick}>
            <RemoveIcon fontSize="small"/>
        </IconButton>
    </Tooltip>;
};

const mapStateToProps = (state: ApplicationState) => ({
    query: state.searchResult.query,
    user: state.userState.user,
    selectedSettings: getSelectedSearchSettings(state),
});

const mapDispatchToProps = {
    startSearching: startSearchingAction as (query: string, user: User, searchSettings: SearchSettings, history?: H.History) => void,
    openSnackbar
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(LimitSearchButton))