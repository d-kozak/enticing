import {WithStyles} from "@material-ui/core";
import * as React from "react";
import {useState} from "react";
import withStyles from "@material-ui/core/es/styles/withStyles";
import createStyles from "@material-ui/core/es/styles/createStyles";
import Typography from "@material-ui/core/Typography";
import SearchInput from "../../components/searchbar/SearchInput";
import {Theme} from "@material-ui/core/es";
import {AppState} from "../../AppState";
import {connect} from "react-redux";

const styles = (theme: Theme) => createStyles({
    mainDiv: {
        position: 'fixed',
        top: '40%',
        left: '50%',
        transform: 'translate(-50%, -50%)'
    },
    title: {
        marginBottom: '30px',
        fontSize: 'calc(3vh + 3em)',
        textAlign: 'center'
    }
});

export interface CenteredSearchBar extends WithStyles<typeof styles> {
    startSearching: (query: string) => void;
    lastQuery: string;
}

const CenteredSearchBar = (props: CenteredSearchBar) => {
    const {classes, startSearching, lastQuery} = props;

    const [query, setQuery] = useState(lastQuery);

    return <div className={classes.mainDiv}>
        <Typography className={classes.title} variant="h3">
            Corproc Search
        </Typography>
        <SearchInput query={query} setQuery={setQuery} startSearching={startSearching}/>
    </div>
};

const mapStateToProps = (state: AppState) => ({
    lastQuery: state.query.lastQuery
});

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps)(CenteredSearchBar));