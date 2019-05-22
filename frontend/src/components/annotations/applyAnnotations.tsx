import {AnnotatedText, AnnotationPosition, QueryMapping, validateAnnotatedText} from "../../entities/Annotation";
import React from "react";
import AnnotatedElement from "./AnnotatedElement";
import EnrichedText from "./EnrichedText";

export const enrichText = (annotatedText: AnnotatedText): React.ReactNode => {
    validateAnnotatedText(annotatedText);
    const modifiedText = splitAnnotations(annotatedText);
    return applyAnnotations(modifiedText, [0, modifiedText.text.length]);
    // return applyQueryMapping(modifiedText);
}

export const splitAnnotations = (annotatedText: AnnotatedText): AnnotatedText => {
    const updatedPositions = annotatedText.positions.flatMap(position => splitAnnotation(position, annotatedText.queryMapping));
    return {...annotatedText, positions: updatedPositions}
}

const splitAnnotation = (position: AnnotationPosition, queryMapping: Array<QueryMapping>): Array<AnnotationPosition> => {
    const {from, to, annotationId} = position;

    for (let mapping of queryMapping) {
        const annotationInsideMapping = mapping.from < from && mapping.to > to;
        const mappingInsideAnnotation = from < mapping.from && to > mapping.to;
        const leftOverlap = from < mapping.from && to < mapping.to;
        const rightOverlap = mapping.from < from && to > mapping.to;
        if (mappingInsideAnnotation) {
            return [
                {from, to: mapping.from, annotationId},
                {from: mapping.from, to: mapping.to, annotationId},
                {from: mapping.to, to, annotationId}
            ];
        } else if (leftOverlap) {
            return [
                {from, to: mapping.from, annotationId},
                {from: mapping.from, to, annotationId}
            ]
        } else if (rightOverlap) {
            return [
                {from, to: mapping.to, annotationId},
                {from: mapping.to, to, annotationId}
            ]
        } else if (annotationInsideMapping) {
            return [position];
        }
    }

    return [position];
}

export const applyQueryMapping = (annotatedText: AnnotatedText): React.ReactNode => {
    const {text, queryMapping} = annotatedText;
    if (queryMapping.length == 0)
        return applyAnnotations(annotatedText, [0, text.length]);

    const enriched = queryMapping.map((position, index) => ({
        ...position,
        prevEnd: index > 0 ? queryMapping[index - 1].to : 0
    }))

    const annotated = enriched.map((position, index) => <React.Fragment key={index}>
        {applyAnnotations(annotatedText, [position.prevEnd, position.from])}
        <EnrichedText content={applyAnnotations(annotatedText, [position.from, position.to])}/>
    </React.Fragment>)

    const lastPosition = queryMapping[queryMapping.length - 1];

    return <React.Fragment>
        {annotated}
        {applyAnnotations(annotatedText, [lastPosition.to, text.length])}
    </React.Fragment>

};

/**
 * @param annotatedText text to annotate
 * @param interval interval which should be processed
 * @return ReactDOM tree containing the annotated text
 *
 * @see AnnotatedText
 */
export const applyAnnotations = (annotatedText: AnnotatedText, interval: [Number, Number]): React.ReactNode => {
    const {text, annotations, positions} = annotatedText;
    const processedPositions = positions.filter(position => position.from >= interval[0] && position.to < interval[1]);
    if (processedPositions.length == 0) {
        return annotatedText.text
    }

    /**
     * 1) each position is enriched with the knowledge of where it's preceding position ends
     * */
    const enriched = processedPositions.map((position, index) => ({
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

