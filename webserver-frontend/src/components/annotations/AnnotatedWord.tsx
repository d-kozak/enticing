import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";
import React, {useState} from 'react';
import Tooltip from "@material-ui/core/es/Tooltip";
import AnnotationContent from "./AnnotationContent";
import {Theme} from "@material-ui/core/es";
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import {CorpusFormat} from "../../entities/CorpusFormat";
import {Entity, Word} from "./TextUnitList";


const styles = (theme: Theme) => createStyles({
    tooltip: {
        maxWidth: '500px',
        backgroundColor: theme.palette.common.white,
        border: '1px solid black'
    }
});


export interface AnnotatedWordProps extends WithStyles<typeof styles> {
    corpusFormat: CorpusFormat,
    word: Word,
    enclosingEntity?: Entity,
    color?: string
}

const AnnotatedWord = (props: AnnotatedWordProps) => {
    const {classes, color, word, enclosingEntity: entity, corpusFormat} = props;

    // custom open-close handling was implemented so that it works on mobile phones as well,
    // hover does not work there by itself, clicks are necessary
    const [isOpen, setOpen] = useState(false);
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

    const indexes = Object.keys(corpusFormat.indexes);
    const defaultIndex = indexes.indexOf(corpusFormat.defaultIndex || "token");
    const glueIndex = indexes.indexOf("_glue");

    // last index contains glue info, N means next (there should be no space between this and next character)
    const followSpace = (glueIndex == -1 || word.indexes[glueIndex] != 'N') ? " " : "";
    const text = defaultIndex != -1 ? word.indexes[defaultIndex] + followSpace : " !NULL! ";

    return <React.Fragment>
        <ClickAwayListener onClickAway={() => setOpen(false)}>
            <Tooltip classes={{tooltip: classes.tooltip}}
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
                         <AnnotationContent corpusFormat={corpusFormat} word={word} enclosingEntity={entity}/>
                     </div>}
            >
                <span style={color ? {color} : {}}>{text}</span>
            </Tooltip>
        </ClickAwayListener>
    </React.Fragment>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotatedWord)