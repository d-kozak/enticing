import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import React from 'react';
import Tooltip from "@material-ui/core/es/Tooltip";
import Typography from "@material-ui/core/es/Typography";


const styles = (theme: Theme) => createStyles({
    mapping: {
        fontWeight: "bold",
        padding: '0px 3px',
        margin: '0px 10px'
    },
    tooltip: {
        backgroundColor: theme.palette.common.white,
        border: '1px solid black'
    }
});

type QueryMappingTooltipProps = WithStyles<typeof styles> & {
    content: React.ReactNode
    decoration: string,
}

const QueryMappingTooltip = (props: QueryMappingTooltipProps) => {
    const {classes, content, decoration} = props;

    const tooltip = classes.tooltip;

    const tooltipContent = <Typography variant="h6">'{decoration}'</Typography>

    return <Tooltip
        title={tooltipContent}
        classes={{tooltip}}
        placement="top"
    >
        <span className={classes.mapping}>{content}</span>
    </Tooltip>
};


export default withStyles(styles, {withTheme: true})(QueryMappingTooltip);