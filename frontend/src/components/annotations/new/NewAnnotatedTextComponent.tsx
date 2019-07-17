import {AnnotatedText, Annotation} from "../../../entities/Annotation";
import React from "react";
import AnnotationTooltip from "../AnnotationTooltip";
import QueryMappingTooltip from "../QueryMappingTooltip";
import {Entity, preprocessAnnotatedText, QueryMatch, TextUnit, Word} from "./PreProcessedAnnotatedText";
import {Theme, WithStyles} from "@material-ui/core";
import createStyles from "@material-ui/core/es/styles/createStyles";
import withStyles from "@material-ui/core/styles/withStyles";


const styles = (theme: Theme) => createStyles({});

type AnnotatedTextComponentProps = WithStyles<typeof styles> & {
    text: AnnotatedText
}

const NewAnnotatedTextComponent = (props: AnnotatedTextComponentProps) => {
    const {text} = props;
    try {
        const processed = preprocessAnnotatedText(text);
        return <React.Fragment>
            {processed.content.map((elem, index) => <React.Fragment key={index}>
                {renderElement(elem, text.annotations)}
            </React.Fragment>)}
        </React.Fragment>
    } catch (e) {
        console.error(e.message);
    }
    return <span>Error when processing result</span>
};
export default withStyles(styles, {withTheme: true})(NewAnnotatedTextComponent);


const colors = ["red", "green", "blue"];

export const renderElement = (text: TextUnit, annotations: { [key: string]: Annotation }): React.ReactNode => {
    if (text instanceof Word) {
        const color = colors[Math.floor(Math.random() * colors.length)];
        const annotation: Annotation = {
            id: "NULL", // not really necessary right now
            content: {
                ...text.content
            }
        };
        const token = text.content["token"] + " ";
        delete annotation.content.token;
        if (Object.keys(annotation.content).length > 0)
            return <AnnotationTooltip annotation={annotation} text={token} color={color}/>
        else return <span>{token}</span>
    } else if (text instanceof Entity) {
        const color = colors[Math.floor(Math.random() * colors.length)];
        const children = text.words.map(word => renderElement(word, annotations));
        return <AnnotationTooltip annotation={{id: "NULL", content: text.attributes}} text="" color={color}
                                  children={children}/>
    } else if (text instanceof QueryMatch) {
        const content = <React.Fragment>
            {text.subunits.map((elem, index) => <React.Fragment key={index}>
                    {renderElement(elem, annotations)}
                </React.Fragment>
            )}
        </React.Fragment>
        return <QueryMappingTooltip content={content} decoration="nertag:person"/>
    } else {
        console.error(`unknown text unit type ${text}`)
        return <span>err</span>
    }
}