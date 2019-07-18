import {Annotation} from "../../../entities/Annotation";
import React from "react";
import AnnotationTooltip from "../AnnotationTooltip";
import QueryMappingTooltip from "../QueryMappingTooltip";
import {Entity, NewAnnotatedText, QueryMatch, TextUnit, Word} from "./NewAnnotatedText";
import {Theme, WithStyles} from "@material-ui/core";
import createStyles from "@material-ui/core/es/styles/createStyles";
import withStyles from "@material-ui/core/styles/withStyles";


const styles = (theme: Theme) => createStyles({});

type AnnotatedTextComponentProps = WithStyles<typeof styles> & {
    text: NewAnnotatedText
}

const NewAnnotatedTextComponent = (props: AnnotatedTextComponentProps) => {
    const {text} = props;
    try {
        return <React.Fragment>
            {text.content.map((elem, index) => <React.Fragment key={index}>
                {renderElement(elem)}
            </React.Fragment>)}
        </React.Fragment>
    } catch (e) {
        console.error(e.message);
    }
    return <span>Error when processing result</span>
};
export default withStyles(styles, {withTheme: true})(NewAnnotatedTextComponent);


const colors = ["red", "green", "blue"];

export const renderElement = (text: TextUnit): React.ReactNode => {
    if (text instanceof Word) {
        const color = colors[Math.floor(Math.random() * colors.length)];
        const annotation: Annotation = {
            id: "NULL", // not really necessary right now
            content: {
                ...text.indexes
            }
        };
        const token = text.indexes["token"] + " ";
        delete annotation.content.token;
        if (Object.keys(annotation.content).length > 0)
            return <AnnotationTooltip annotation={annotation} text={token} color={color}/>
        else return <span>{token}</span>
    } else if (text instanceof Entity) {
        const color = colors[Math.floor(Math.random() * colors.length)];
        const children = text.words.map((word, index) => <React.Fragment key={index}>
            {renderElement(word)}
        </React.Fragment>);
        return <span> E {children} E </span>
        // return <AnnotationTooltip annotation={{id: "NULL", content: text.attributes}} text="" color={color}
        //                           children={children}/>
    } else if (text instanceof QueryMatch) {
        const content = <React.Fragment>
            {text.content.map((elem, index) => <React.Fragment key={index}>
                {renderElement(elem)}
                </React.Fragment>
            )}
        </React.Fragment>
        return <QueryMappingTooltip content={content} decoration="nertag:person"/>
    } else {
        console.error(`unknown text unit type ${text}`)
        return <span>err</span>
    }
}