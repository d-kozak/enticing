import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {Annotation} from "../../entities/Annotation";

const styles = createStyles({
    image: {
        margin: '5px',
        maxWidth: '150px',
        maxHeight: '150px'
    }
});


export interface AnnotationContentProps extends WithStyles<typeof styles> {
    annotation: Annotation;
}

const AnnotationContent = (props: AnnotationContentProps) => {
    const {annotation, classes} = props;
    return <div>
        {annotation.image && <img className={classes.image} src={annotation.image}/>}
        {annotation.content.map(
            (value, index) => <div key={index}>
                {value.name} = {value.value}
            </div>
        )}
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotationContent)