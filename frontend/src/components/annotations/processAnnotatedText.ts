import {
    AnnotatedText,
    AnnotationPosition,
    MatchedRegion,
    QueryMapping,
    validateAnnotatedText
} from "../../entities/Annotation";
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
    const {match, annotationId} = position;
    const {from, to} = match

    for (let mapping of queryMapping) {
        const index = mapping.textIndex;
        const annotationInsideMapping = index.from < from && index.to > to;
        const mappingInsideAnnotation = from < index.from && to > index.to;
        const leftOverlap = from < index.from && to < index.to;
        const rightOverlap = index.from < from && to > index.to;
        if (mappingInsideAnnotation) {
            return [
                {annotationId, match: MatchedRegion.fromInterval(from, index.from)},
                {annotationId, match: MatchedRegion.fromInterval(index.from, index.to)},
                {annotationId, match: MatchedRegion.fromInterval(index.to, to)}
            ];
        } else if (leftOverlap) {
            return [
                {annotationId, match: MatchedRegion.fromInterval(from, index.from)},
                {annotationId, match: MatchedRegion.fromInterval(index.from, to)}
            ]
        } else if (rightOverlap) {
            return [
                {annotationId, match: MatchedRegion.fromInterval(from, index.to)},
                {annotationId, match: MatchedRegion.fromInterval(index.to, to)}
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
        prevEnd: index > 0 ? queryMapping[index - 1].textIndex.to : 0
    }))

    const annotated = enriched.flatMap(position => [
        ...processAnnotations(annotatedText, [position.prevEnd, position.textIndex.from]),
        new TextWithDecoration(processAnnotations(annotatedText, [position.textIndex.from, position.textIndex.to]), new Decoration('here comes the sun, ehm query...'))
    ])

    const lastPosition = queryMapping[queryMapping.length - 1];
    annotated.push(...processAnnotations(annotatedText, [lastPosition.textIndex.to, text.length]))
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
    const processedPositions = positions.filter(position => position.match.from >= interval[0] && position.match.to <= interval[1]);
    if (processedPositions.length == 0) {
        return [annotatedText.text.substring(interval[0], interval[1])]
    }

    /**
     * 1) each position is enriched with the knowledge of where it's preceding position ends
     * */
    const enriched = processedPositions.map((position, index) => ({
        ...position,
        prevEnd: index > 0 ? positions[index - 1].match.to : interval[0]
    }));

    /**
     * 2) each position is mapped to a tuple of a) normal string consisting of input[prevPosition,annotationstart-1]
     *                                          b) the annotation itself
     */
    const annotated = enriched.flatMap(position => [
        text.substring(position.prevEnd, position.match.from),
        new TextWithAnnotation(text.substring(position.match.from, position.match.to), position.annotationId)
    ]);

    /**
     * 3) append the remaining text
     */
    const lastPosition = positions[positions.length - 1];

    if (lastPosition.match.to < interval[1])
        annotated.push(text.substring(lastPosition.match.to, interval[1]))

    return annotated;
};

