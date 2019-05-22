import {dumpMap} from "../utils/dumps";

export type color = 'blue' | 'green' | 'red' | 'orange' | 'purple';

export interface Annotation {
    id: number;
    text: string;
    color: color;
    content: Map<string, string>;
    image?: string;
    type: string;
}

export interface AnnotationPosition {
    annotationId: number;
    from: number;
    to: number;
}

export interface QueryMapping {
    from: number,
    to: number,
    query: string
}

/**
 *
 * Represents annotated text
 * @param text string which should be enriched
 * @param annotations map of ids to annotations
 * @param positions position of annotations, required to be ascending
 *
 * preconditions:
 * a) the values in positions array are ascending
 * b) all annotationIds in positions must be valid id annotations referring to an annotation in the map
 */
export interface AnnotatedText {
    text: string;
    annotations: Map<number, Annotation>;
    positions: Array<AnnotationPosition>;
    queryMapping: Array<QueryMapping>;
}


/**
 * checks the preconditions described in AnnotatedText doc
 * @param annotatedText
 * @see AnnotatedText
 * @throws Error if invalid
 */
export const validateAnnotatedText = (annotatedText: AnnotatedText): void => {
    const {positions, annotations, queryMapping} = annotatedText;
    for (let i in annotatedText.positions) {
        const index = Number(i);
        const position = positions[index];
        assert(position.from < position.to, () => `position.from must be smaller than positions.to, in ${position}`);
        if (index < positions.length - 1) {
            const next = positions[index + 1];
            assert(position.to <= next.from, () => `position.to must be smaller than next.from, in ${position},${next}`);
        }
        assert(annotations.get(position.annotationId) !== undefined,
            () => `annotation with id ${position.annotationId} not found in ${dumpMap(annotations)}`
        );

    }
    for (let i in queryMapping) {
        const index = Number(i);
        const mapping = queryMapping[index];
        assert(mapping.from < mapping.to, () => `mapping.from must be smaller than mapping.to, in ${mapping}`);
        if (index < queryMapping.length - 1) {
            const next = queryMapping[index + 1];
            assert(mapping.to <= next.from, () => `mapping.to must be smaller than next.from, in ${mapping},${next}`);
        }
    }
};

const assert = (condition: boolean, lazyMessage: () => string) => {
    if (!condition) {
        const message = lazyMessage();
        console.error(message);
        throw new Error(lazyMessage());
    }
};
