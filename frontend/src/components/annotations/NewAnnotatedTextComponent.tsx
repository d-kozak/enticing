import React from "react";
import AnnotatedWord from "./AnnotatedWord";
import QueryMappingTooltip from "./QueryMappingTooltip";
import {Entity, NewAnnotatedText, QueryMatch, TextUnit, Word} from "./NewAnnotatedText";
import {Theme, WithStyles} from "@material-ui/core";
import createStyles from "@material-ui/core/es/styles/createStyles";
import withStyles from "@material-ui/core/styles/withStyles";
import {CorpusFormat} from "../../entities/CorpusFormat";


const styles = (theme: Theme) => createStyles({
    root: {
        margin: '0px 15px 5px 15px'
    }
});

type AnnotatedTextComponentProps = WithStyles<typeof styles> & {
    text: NewAnnotatedText,
    corpusFormat: CorpusFormat
}

const NewAnnotatedTextComponent = (props: AnnotatedTextComponentProps) => {
    const {text, corpusFormat, classes} = props;
    try {
        return <div className={classes.root}>
            {text.content.map((elem, index) => <React.Fragment key={index}>
                {renderElement(elem, corpusFormat)}
            </React.Fragment>)}
        </div>
    } catch (e) {
        console.error(e.message);
    }
    return <span>Error when processing result</span>
};
export default withStyles(styles, {withTheme: true})(NewAnnotatedTextComponent);


const colors = ["red", "green", "blue"];

export const renderElement = (text: TextUnit, corpusFormat: CorpusFormat): React.ReactNode => {
    if (text instanceof Word) {
        if (text.indexes.length === 1) return <span>{text.indexes[0] + " "}</span>
        return <AnnotatedWord word={text} corpusFormat={corpusFormat}/>
    } else if (text instanceof Entity) {
        const color = colors[Math.floor(Math.random() * colors.length)];
        return <React.Fragment>
            {text.words.map((word, i) => <AnnotatedWord key={i} word={word} corpusFormat={corpusFormat}
                                                        enclosingEntity={text} color={color}/>)}
        </React.Fragment>
    } else if (text instanceof QueryMatch) {
        const content = <React.Fragment>
            {text.content.map((elem, index) => <React.Fragment key={index}>
                {renderElement(elem, corpusFormat)}
                </React.Fragment>
            )}
        </React.Fragment>
        return <QueryMappingTooltip content={content} decoration="nertag:person"/>
    } else {
        console.error(`unknown text unit type ${text}`)
        return <span>err</span>
    }
}