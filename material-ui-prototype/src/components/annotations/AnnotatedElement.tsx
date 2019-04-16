import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";
import React from 'react';
import Tooltip from "@material-ui/core/es/Tooltip";


const styles = createStyles({
    tooltipTrigger: {
        color: 'red'
    }
});


export interface AnnotatedElementProps extends WithStyles<typeof styles> {
    text: string,
    id: string
}

const colors = ['blue', 'green', 'red', 'orange', 'purple'];

const AnnotatedElement = (props: AnnotatedElementProps) => {
    const {text, classes, id} = props;

    const color = colors[Number(id) % colors.length];
    const style = {color}

    return <React.Fragment>
        <Tooltip title={<div>
            <h1>Tooltip</h1>
            <p>Text</p>
        </div>}>
            <span style={style} className={classes.tooltipTrigger}>{text}</span>
        </Tooltip>
    </React.Fragment>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotatedElement)