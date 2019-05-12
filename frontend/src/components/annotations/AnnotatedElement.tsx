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
    annotation: Annotation,
    text: string,
    color: string
}

const AnnotatedElement = (props: AnnotatedElementProps) => {
    const {annotation, text, color, classes} = props;

    // custom open-close handling was implemented so that it works on mobile phones as well,
    // hover does not work there by itself, clicks are necessary
    const [isOpen, setOpen] = useState(false)
    let shouldClose = true;

    const handleMouseLeave = () => {
        if (shouldClose) {
            setTimeout(() => {
                if (shouldClose) {
                    setOpen(false)
                }
            }, 250);
        }
    };

    const style = {
        color
    }

    const tooltip = classes.tooltip;


    return <React.Fragment>
        <ClickAwayListener onClickAway={() => setOpen(false)}>
            <Tooltip classes={{tooltip}}
                     open={isOpen}
                     interactive
                     disableHoverListener
                     onClick={() => setOpen(true)}
                     onMouseEnter={() => setOpen(true)}
                     onMouseLeave={handleMouseLeave}
                     onClose={handleMouseLeave}
                     title={<div onMouseEnter={() => {
                         shouldClose = false;
                     }} onMouseLeave={() => {
                         shouldClose = true;
                         setOpen(false);
                     }}>
                         <AnnotationContent annotation={annotation}/>
                     </div>}
            >
                <span style={style}>{text}</span>
            </Tooltip>
        </ClickAwayListener>
    </React.Fragment>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotatedElement)