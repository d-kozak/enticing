import React from "react";
import AnnotatedWord from "./AnnotatedWord";
import QueryMappingTooltip from "./QueryMappingTooltip";
import {Entity, QueryMatch, TextUnit, TextUnitList, Word} from "./TextUnitList";
import {Theme, WithStyles} from "@material-ui/core";
import createStyles from "@material-ui/core/es/styles/createStyles";
import withStyles from "@material-ui/core/styles/withStyles";
import {CorpusFormat} from "../../entities/CorpusFormat";
import {SelectedMetadata} from "../../entities/SelectedMetadata";
import {consoleDump} from "../utils/dump";


const styles = (theme: Theme) => createStyles({
    root: {
        margin: '0px 15px 5px 15px'
    }
});

type AnnotatedTextComponentProps = WithStyles<typeof styles> & {
    text: TextUnitList,
    query: string
    corpusFormat: CorpusFormat,
    metadata: SelectedMetadata | null
    showParagraphs: boolean
}

const TextUnitListComponent = (props: AnnotatedTextComponentProps) => {
    const {text, query, corpusFormat, metadata, classes, showParagraphs} = props;
    let tokenIndex = Object.keys(corpusFormat.indexes).indexOf("token");
    try {
        return <div className={classes.root}>
            {text.content.map((elem, index) => <React.Fragment key={index}>
                {renderElement(elem, query, corpusFormat, metadata, tokenIndex, showParagraphs)}
            </React.Fragment>)}
        </div>
    } catch (e) {
        console.error(e.message);
    }
    return <span>Error when processing result</span>
};
export default withStyles(styles, {withTheme: true})(TextUnitListComponent);


function chooseColor(entity: Entity, metadata: SelectedMetadata | null): string {
    if (metadata != null) {
        const entityInfo = metadata.entities[entity.entityClass];
        if (entityInfo) return entityInfo.color;
    }
    console.warn(`could not selected color for ${entity.entityClass}`);
    consoleDump(metadata);
    return "red";
}

export const renderElement = (text: TextUnit, query: string, corpusFormat: CorpusFormat, metadata: SelectedMetadata | null, tokenIndex: number, showParagraphs: boolean): React.ReactNode => {
    if (text instanceof Word) {
        if (tokenIndex != -1) {
            if (text.indexes[tokenIndex] === "¶")
                return <span/>;
            if (text.indexes[tokenIndex] === "§")
                return <span>
                    {showParagraphs && <br/>}
                    {showParagraphs && <br/>}
                </span>
        }
        if (text.indexes.length === 1) return text.indexes[0] + (text.indexes[text.indexes.length - 1] != 'N' ? ' ' : '');
        return <AnnotatedWord word={text} corpusFormat={corpusFormat}/>
    } else if (text instanceof Entity) {
        const color = chooseColor(text, metadata);
        return <React.Fragment>
            {text.words.map((word, i) => <AnnotatedWord key={i} word={word} corpusFormat={corpusFormat}
                                                        enclosingEntity={text} color={color}/>)}
        </React.Fragment>
    } else if (text instanceof QueryMatch) {
        const content = <React.Fragment>
            {text.content.map((elem, index) => <React.Fragment key={index}>
                {renderElement(elem, query, corpusFormat, metadata, tokenIndex, showParagraphs)}
                </React.Fragment>
            )}
        </React.Fragment>
        let decoration = "unknown";
        if (text.queryMatch.from >= 0 && text.queryMatch.from <= text.queryMatch.to && text.queryMatch.to < query.length - 1) {
            decoration = query.substring(text.queryMatch.from, text.queryMatch.to + 1)
        }
        return <QueryMappingTooltip content={content} decoration={decoration}/>
    } else {
        console.error(`unknown text unit type ${text}`);
        return <span>err</span>
    }
};