import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {connect} from "react-redux";
import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {ApplicationState} from "../../ApplicationState";


const styles = (theme: Theme) => createStyles({
    root: {
        margin: '10 px'
    }
});

type SearchStatisticsProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {}

const SearchStatistics = (props: SearchStatisticsProps) => {
    const {snippetCount, statistics, classes} = props;
    let text = snippetCount > 0 ? `${snippetCount} of snippets returned. ` : 'No snippets found. ';
    if (statistics) {
        text += `Backend took ${(statistics.backendTime / 1000).toFixed(2)} seconds, frontend took ${(statistics.frontendTime / 1000).toFixed(2)} seconds.`
    }
    return <div className={classes.root}>
        <Typography variant="body1">{text}</Typography>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    snippetCount: state.searchResult.snippetIds.length,
    statistics: state.searchResult.statistics
});
const mapDispatchToProps = {};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(SearchStatistics));