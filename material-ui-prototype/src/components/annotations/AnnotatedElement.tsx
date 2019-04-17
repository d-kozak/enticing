import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";
import React from 'react';
import Tooltip from "@material-ui/core/es/Tooltip";
import {Annotation} from "../../entities/Annotation";
import AnnotationContent from "./AnnotationContent";


const styles = createStyles({
    tooltip: {
        maxWidth: '500px'
    }
});


export interface AnnotatedElementProps extends WithStyles<typeof styles> {
    annotation: Annotation
}

const AnnotatedElement = (props: AnnotatedElementProps) => {
    const {annotation, classes} = props;

    const style = {
        color: annotation.color
    }

    const tooltip = classes.tooltip;

    return <React.Fragment>
        <Tooltip interactive classes={{tooltip}} title={<AnnotationContent annotation={annotation}/>}>
            <span style={style}>{annotation.text} </span>
        </Tooltip>
    </React.Fragment>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotatedElement)