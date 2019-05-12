import {AnnotatedText, validateAnnotatedText} from "../../entities/Annotation";
import React from "react";
import AnnotatedElement from "./AnnotatedElement";

/**
 * @param annotatedText text to annotate
 * @return ReactDOM tree containing the annotated text
 *
 * @see AnnotatedText
 */
export const applyAnnotations = (annotatedText: AnnotatedText): React.ReactNode => {
    const {text, annotations, positions} = annotatedText;
    if (positions.length == 0) {
        return annotatedText.text
    }
    validateAnnotatedText(annotatedText);

    /**
     * 1) each position is enriched with the knowledge of where it's preceding position ends
     * */
    const enriched = positions.map((position, index) => ({
        ...position,
        prevEnd: index > 0 ? positions[index - 1].to : 0
    }));

    /**
     * 2) each position is mapped to a tuple of a) normal string consisting of input[prevPosition,annotationstart-1]
     *                                          b) the annotation itself
     */
    const annotated = enriched.map((position, index) => <React.Fragment key={index}>
        {text.substring(position.prevEnd, position.from)}
        <AnnotatedElement text={text.substring(position.from, position.to)} color="red"
                          annotation={annotations.get(position.annotationId)!}/>
    </React.Fragment>)

    /**
     * 3) append the remaining text
     */
    const lastPosition = positions[positions.length - 1];

    return <React.Fragment>
        {annotated}
        {text.substring(lastPosition.to)}
    </React.Fragment>
};

