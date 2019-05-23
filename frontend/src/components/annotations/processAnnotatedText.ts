import {AnnotatedText, AnnotationPosition, QueryMapping, validateAnnotatedText} from "../../entities/Annotation";
import {Decoration, ProcessedAnnotatedText, TextWithAnnotation, TextWithDecoration} from "./ProcessedAnnotatedText";


const isNotEmpty = (text: ProcessedAnnotatedText): boolean => {
    if (typeof text === "string")
        return text.length > 0;
    else if (text instanceof TextWithAnnotation) {
        return text.text.length > 0;
    } else {
        return text.text.length > 0 && text.text.every(isNotEmpty);
    }
}

export const removeEmpty = (text: Array<ProcessedAnnotatedText>): Array<ProcessedAnnotatedText> => {
    return text.map(
        elem => {
            if (elem instanceof TextWithDecoration) {
                const newText = elem.text.filter(isNotEmpty)
                return new TextWithDecoration(newText, elem.decoration)
            } else {
                return elem;
            }
        }
    ).filter(isNotEmpty)


}

export const processAnnotatedText = (annotatedText: AnnotatedText): [AnnotatedText, Array<ProcessedAnnotatedText>] => {
    validateAnnotatedText(annotatedText);
    const modifiedText = splitAnnotations(annotatedText);
    const processed = processQueryMapping(modifiedText)
    return [modifiedText, removeEmpty(processed)];
}

export const splitAnnotations = (annotatedText: AnnotatedText): AnnotatedText => {
    const updatedPositions = annotatedText.positions.flatMap(position => splitAnnotation(position, annotatedText.queryMapping));
    return {...annotatedText, positions: updatedPositions}
}

export const splitAnnotation = (position: AnnotationPosition, queryMapping: Array<QueryMapping>): Array<AnnotationPosition> => {
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

export const processQueryMapping = (annotatedText: AnnotatedText): Array<ProcessedAnnotatedText> => {
    const {text, queryMapping} = annotatedText;
    if (queryMapping.length == 0)
        return processAnnotations(annotatedText, [0, text.length]);

    const enriched = queryMapping.map((position, index) => ({
        ...position,
        prevEnd: index > 0 ? queryMapping[index - 1].to : 0
    }))

    const annotated = enriched.flatMap((position, index) => [
        ...processAnnotations(annotatedText, [position.prevEnd, position.from]),
        new TextWithDecoration(processAnnotations(annotatedText, [position.from, position.to]), new Decoration(position.query))
    ])

    const lastPosition = queryMapping[queryMapping.length - 1];
    annotated.push(...processAnnotations(annotatedText, [lastPosition.to, text.length]))
    return annotated;
};

/**
 * @param annotatedText text to annotate
 * @param interval interval which should be processed
 * @return ReactDOM tree containing the annotated text
 *
 * @see AnnotatedText
 */
export const processAnnotations = (annotatedText: AnnotatedText, interval: [number, number]): Array<string | TextWithAnnotation> => {
    const {text, positions} = annotatedText;
    const processedPositions = positions.filter(position => position.from >= interval[0] && position.to <= interval[1]);
    if (processedPositions.length == 0) {
        return [annotatedText.text.substring(interval[0], interval[1])]
    }

    /**
     * 1) each position is enriched with the knowledge of where it's preceding position ends
     * */
    const enriched = processedPositions.map((position, index) => ({
        ...position,
        prevEnd: index > 0 ? positions[index - 1].to : interval[0]
    }));

    /**
     * 2) each position is mapped to a tuple of a) normal string consisting of input[prevPosition,annotationstart-1]
     *                                          b) the annotation itself
     */
    const annotated = enriched.flatMap((position, index) => [
        text.substring(position.prevEnd, position.from),
        new TextWithAnnotation(text.substring(position.from, position.to), position.annotationId)
    ]);

    /**
     * 3) append the remaining text
     */
    const lastPosition = positions[positions.length - 1];

    if (lastPosition.to < interval[1])
        annotated.push(text.substring(lastPosition.to, interval[1]))

    return annotated;
};

