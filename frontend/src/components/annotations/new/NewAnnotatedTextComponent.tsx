import {Annotation} from "../../../entities/Annotation";
import React from "react";
import AnnotationTooltip from "../AnnotationTooltip";
import QueryMappingTooltip from "../QueryMappingTooltip";
import {Entity, NewAnnotatedText, QueryMatch, TextUnit, Word} from "./NewAnnotatedText";
import {Theme, WithStyles} from "@material-ui/core";
import createStyles from "@material-ui/core/es/styles/createStyles";
import withStyles from "@material-ui/core/styles/withStyles";
import {CorpusFormat} from "../../../entities/CorpusFormat";


const styles = (theme: Theme) => createStyles({});

type AnnotatedTextComponentProps = WithStyles<typeof styles> & {
    text: NewAnnotatedText,
    corpusFormat: CorpusFormat
}

const NewAnnotatedTextComponent = (props: AnnotatedTextComponentProps) => {
    const {text, corpusFormat} = props;
    try {
        return <React.Fragment>
            {text.content.map((elem, index) => <React.Fragment key={index}>
                {renderElement(elem, corpusFormat)}
            </React.Fragment>)}
        </React.Fragment>
    } catch (e) {
        console.error(e.message);
    }
    return <span>Error when processing result</span>
};
export default withStyles(styles, {withTheme: true})(NewAnnotatedTextComponent);


const colors = ["red", "green", "blue"];

export const renderElement = (text: TextUnit, corpusFormat: CorpusFormat): React.ReactNode => {
    if (text instanceof Word) {
        //const color = "black" // todo choose color colors[Math.floor(Math.random() * colors.length)];

        const content = {} as { [clazz: string]: string }
        const indexNames = Object.keys(corpusFormat.indexes)
        for (let i in text.indexes) {
            content[indexNames[i]] = text.indexes[i]
        }
        const annotation: Annotation = {
            id: "NULL", // not really necessary right now
            content
        };
        const tokenIndex = indexNames.findIndex(name => name === "token");
        const token = text.indexes[tokenIndex] + " ";
        delete annotation.content.token;
        if (Object.keys(annotation.content).length > 0)
            return <AnnotationTooltip annotation={annotation} text={token}/>
        else return <span>{token}</span>
    } else if (text instanceof Entity) {
        const color = colors[Math.floor(Math.random() * colors.length)];
        const children = text.words.map((word, index) => <React.Fragment key={index}>
            {renderElement(word, corpusFormat)}
        </React.Fragment>);

        const entityInfo = corpusFormat.entities[text.entityClass]
        if (!entityInfo) {
            console.error("No attributes found for entity " + text.entityClass)
            return <span>{children}</span>
        }
        const content = {} as { [key: string]: string };
        const attributeNames = Object.keys(entityInfo.attributes);
        for (let i in text.attributes) {
            content[attributeNames[i]] = text.attributes[i];
        }
        return <AnnotationTooltip annotation={{id: "NULL", content}} text="" color={color}
                                  children={children}/>
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