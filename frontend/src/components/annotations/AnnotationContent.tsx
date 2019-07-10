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
    const content = annotation.content;
    return <div>
        <Typography variant="h4">{content["type"]}</Typography>
        {content["image"] && <img className={classes.image} src={content["image"]}/>}
        {Object.keys(content).map(
            (name, index) => <div key={index}>
                <Typography variant="body1"><span
                    className={classes.attributeName}>{name}:</span> {name == 'url' ?
                    <a href={content[name]}>{content[name]}</a> : content[name]}</Typography>
            </div>
        )}
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotationContent)