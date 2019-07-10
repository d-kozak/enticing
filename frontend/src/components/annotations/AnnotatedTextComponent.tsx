import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import React from 'react';
import {AnnotatedText, Annotation} from "../../entities/Annotation";
import {processAnnotatedText} from "./processAnnotatedText";

import {ProcessedAnnotatedText, TextWithAnnotation} from "./ProcessedAnnotatedText";
import AnnotationTooltip from "./AnnotationTooltip";
import QueryMappingTooltip from "./QueryMappingTooltip";


const styles = (theme: Theme) => createStyles({});

type AnnotatedTextComponentProps = WithStyles<typeof styles> & {
    text: AnnotatedText
}

const AnnotatedTextComponent = (props: AnnotatedTextComponentProps) => {
    const {text} = props;
    const [annotation, processedText] = processAnnotatedText(text);
    return <React.Fragment>
        {processedText.map((elem, index) => <React.Fragment key={index}>
            {visualizeText(elem, annotation.annotations)}
        </React.Fragment>)}
    </React.Fragment>
};
export default withStyles(styles, {withTheme: true})(AnnotatedTextComponent);


const colors = ["red", "green", "blue"];

const visualizeText = (text: ProcessedAnnotatedText, annotations: { [key: string]: Annotation }): React.ReactNode => {
    if (typeof text === "string") {
        return text;
    } else if (text instanceof TextWithAnnotation) {
        const color = colors[Math.floor(Math.random() * colors.length)]
        return <AnnotationTooltip annotation={annotations[text.annotationId]} text={text.text} color={color}/>
    } else {
        const content = <React.Fragment>
            {text.text.map((elem, index) => <React.Fragment key={index}>
                    {visualizeText(elem, annotations)}
                </React.Fragment>
            )}
        </React.Fragment>
        return <QueryMappingTooltip content={content} decoration={text.decoration.text}/>
    }

}
