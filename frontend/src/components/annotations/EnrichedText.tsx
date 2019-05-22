import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import React from 'react';


const styles = (theme: Theme) => createStyles({
    mapping: {
        backgroundColor: "red"
    }
});

type EnrichedTextProps = WithStyles<typeof styles> & {
    content: React.ReactNode
}

const EnrichedText = (props: EnrichedTextProps) => {
    const {classes, content} = props;
    return <span className={classes.mapping}>
        {content}
    </span>
};


export default withStyles(styles, {withTheme: true})(EnrichedText);