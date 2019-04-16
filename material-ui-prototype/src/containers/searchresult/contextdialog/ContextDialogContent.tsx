import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {SearchResultContext} from "../../../entities/SearchResultContext";
import {applyAnnotations} from "../../../components/annotations/applyAnnotations";

const styles = createStyles({
    titleUrl: {
        margin: "10px 0px"
    }
});


export interface ContextDialogContentProps extends WithStyles<typeof styles> {
    context: SearchResultContext
}

const ContextDialogContent = (props: ContextDialogContentProps) => {
    const {context, classes} = props;
    return <div>
        <Typography className={classes.titleUrl} variant="caption"><a
            href={context.url}>{context.url}</a></Typography>
        {applyAnnotations(context.text)}
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(ContextDialogContent)