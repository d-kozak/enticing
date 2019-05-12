import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {Annotation} from "../../entities/Annotation";
import Typography from "@material-ui/core/es/Typography";

const styles = createStyles({
    image: {
        margin: '5px',
        maxWidth: '150px',
        maxHeight: '150px',
    },
    attributeName: {
        fontWeight: 'bold'
    }
});


export interface AnnotationContentProps extends WithStyles<typeof styles> {
    annotation: Annotation;
}

const AnnotationContent = (props: AnnotationContentProps) => {
    const {annotation, classes} = props;
    return <div>
        <Typography variant="h4">{annotation.type}</Typography>
        {annotation.image && <img className={classes.image} src={annotation.image}/>}
        {Array.from(annotation.content).map(
            ([name, value], index) => <div key={index}>
                <Typography variant="body1"><span
                    className={classes.attributeName}>{name}:</span> {name == 'url' ?
                    <a href={value}>{value}</a> : value}</Typography>
            </div>
        )}
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotationContent)