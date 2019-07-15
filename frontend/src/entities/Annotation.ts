import {validateOrNull} from "./validationUtils";
import * as yup from "yup";
import {mapValues} from 'lodash';


export type color = 'blue' | 'green' | 'red' | 'orange' | 'purple';

export interface Annotation {
    id: string;
    content: { [key: string]: string };
}

export const annotationSchema = yup.object({
    id: yup.string()
        .required()
        .min(1),
    content: yup.object()
        .required()
})

export function isAnnotation(obj: Object): obj is Annotation {
    return validateOrNull(annotationSchema, obj) !== null;
}


export class MatchedRegion {

    constructor(public from: number = 0, public size: number = 0) {
    }

    get to() {
        return this.from + this.size;
    }

    static fromInterval(from: number, to: number): MatchedRegion {
        return new MatchedRegion(from, to - from);
    }
}

const matchRegionSchema = yup.object({
    from: yup.number()
        .required()
        .min(0),
    size: yup.number()
        .required()
        .positive()
})

export function isMatchedRegion(obj: Object): obj is MatchedRegion {
    return validateOrNull(matchRegionSchema, obj) !== null;
}

/**
 * Opposed to the definition of AnnotationPositions in Kotlin, this one directly
 * rejects more then one level nesting, which is probably what we end up with.
 */
export interface AnnotationPosition {
    annotationId: string;
    match: MatchedRegion;
    subAnnotations?: Array<{
        annotationId: string;
        match: MatchedRegion;
    }>
}

/**
 * We could use yup.lazy and support recursion, but this way we can
 * use yup to ensure at most one inner level, which is what we want right one (entities with words inside)
 */
const annotationPositionSchema = yup.object({
    annotationId: yup.string().required().min(1),
    match: matchRegionSchema,
    subAnnotations: yup.array()
        .of(yup.object({
            annotationId: yup.string().required().min(1),
            match: matchRegionSchema
        }))
});

export function isAnnotationPosition(obj: Object): obj is AnnotationPosition {
    return validateOrNull(annotationPositionSchema, obj) !== null;
}


export interface QueryMapping {
    textIndex: MatchedRegion;
    queryIndex: MatchedRegion;
}

export const queryMappingSchema = yup.object(
    {
        textIndex: matchRegionSchema,
        queryIndex: matchRegionSchema
    }
)

export function isQueryMapping(obj: Object): obj is QueryMapping {
    return validateOrNull(queryMappingSchema, obj) !== null
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
    annotations: { [key: string]: Annotation };
    positions: Array<AnnotationPosition>;
    queryMapping: Array<QueryMapping>;
}


export const annotatedTextSchema = yup.object({
    text: yup.string()
        .required()
        .min(1),
    annotations: yup.lazy(obj => yup.object(
        mapValues(obj, () => annotationSchema)
    )),
    positions: yup.array().of(annotationPositionSchema),
    queryMapping: yup.array().of(queryMappingSchema)
});

export function isAnnotatedText(obj: Object): obj is AnnotatedText {
    return validateOrNull(annotatedTextSchema, obj) !== null;
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
        if (index < positions.length - 1) {
            const next = positions[index + 1];
            assert(position.match.from + position.match.size <= next.match.from, () => `each position must end before the next one starts, in ${JSON.stringify(position)},${JSON.stringify(next)}`);
        }
        assert(annotations[position.annotationId] !== undefined,
            () => `annotation with id ${position.annotationId} not found in ${JSON.stringify(annotations, null, 2)}`
        );

    }
    for (let i in queryMapping) {
        const index = Number(i);
        const mapping = queryMapping[index];
        if (index < queryMapping.length - 1) {
            const next = queryMapping[index + 1];
            assert(mapping.textIndex.from + mapping.textIndex.size <= next.textIndex.from, () => `each mapping must end before the next starts, in ${mapping},${next}`);
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
