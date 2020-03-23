import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";
import React from 'react';
import Tooltip from "@material-ui/core/es/Tooltip";
import AnnotationContent from "./AnnotationContent";
import {Theme} from "@material-ui/core/es";
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
    queryMatch?: string,
    color?: string
}

const AnnotatedWord = (props: AnnotatedWordProps) => {
    const {classes, color, word, enclosingEntity: entity, queryMatch, corpusFormat} = props;

    const indexes = Object.keys(corpusFormat.indexes);
    const defaultIndex = indexes.indexOf(corpusFormat.defaultIndex || "token");
    const glueIndex = indexes.indexOf("_glue");

    // last index contains glue info, N means next (there should be no space between this and next character)
    const followSpace = (glueIndex == -1 || word.indexes[glueIndex] != 'N') ? " " : "";
    const text = defaultIndex != -1 ? word.indexes[defaultIndex] + followSpace : " !NULL! ";

    let style: { [key: string]: string } = {};
    if (color)
        style["color"] = color;
    if (queryMatch)
        style["fontWeight"] = "bold";

    return <React.Fragment>
        <Tooltip
            enterDelay={750}
            interactive={true}
            classes={{tooltip: classes.tooltip}}
            title={<AnnotationContent text={text} corpusFormat={corpusFormat} word={word} enclosingEntity={entity}
                                      queryMatch={queryMatch}/>}>
            <span style={style}>{text}</span>
        </Tooltip>
    </React.Fragment>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotatedWord)