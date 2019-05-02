import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";
import React, {useState} from 'react';
import Tooltip from "@material-ui/core/es/Tooltip";
import {Annotation} from "../../entities/Annotation";
import AnnotationContent from "./AnnotationContent";
import {Theme} from "@material-ui/core/es";
import ClickAwayListener from '@material-ui/core/ClickAwayListener';


const styles = (theme: Theme) => createStyles({
    tooltip: {
        maxWidth: '500px',
        backgroundColor: theme.palette.common.white,
        border: '1px solid black'
    }
});


export interface AnnotatedElementProps extends WithStyles<typeof styles> {
    annotation: Annotation
}

const AnnotatedElement = (props: AnnotatedElementProps) => {
    const {annotation, classes} = props;

    const [isOpen, setOpen] = useState(false)

    const style = {
        color: annotation.color
    }

    const tooltip = classes.tooltip;

    return <React.Fragment>
        <ClickAwayListener onClickAway={() => setOpen(false)}>
            <Tooltip classes={{tooltip}}
                     title={<AnnotationContent annotation={annotation}/>}>
                <span style={style}>{annotation.text} </span>
            </Tooltip>
        </ClickAwayListener>
    </React.Fragment>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotatedElement)