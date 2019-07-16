import {EnticingObject} from "./EnticingObject";
import {AnnotatedText, AnnotationPosition, QueryMapping, validateAnnotatedText} from "../../../entities/Annotation";


export type TextUnit = Word | Entity | QueryMatch;

export type ContentMap = { [key: string]: string };

export class Interval extends EnticingObject {
    constructor(public from: number, public to: number) {
        super();
    }

    isEmpty() {
        return this.from > this.to;
    }
}

export class PreProcessedAnnotatedText extends EnticingObject {

    constructor(public content: Array<TextUnit>) {
        super();
    }
}

export class Word extends EnticingObject {
    constructor(public content: ContentMap) {
        super();
    }
}

export class Entity extends EnticingObject {
    constructor(public attributes: ContentMap, words: Array<Word>) {
        super();
    }
}

export class QueryMatch extends EnticingObject {
    constructor(public queryIndex: [number, number], public subunits: Array<Entity | Word>) {
        super();
    }
}

function getWordsAt(annotatedText: AnnotatedText, interval: Interval): Array<Word> {
    if (interval.isEmpty()) return [];
    const relevantMappings = annotatedText.positions.filter(mapping => mapping.match.from >= interval.from && mapping.match.to <= interval.to)
        .flatMap(mapping => mapping.subAnnotations !== undefined && mapping.subAnnotations.length > 0 ? mapping.subAnnotations : [mapping]);

    return relevantMappings.map(mapping => {
        const metadata = annotatedText.annotations[mapping.annotationId];
        const content = {
            ...metadata.content,
            token: annotatedText.text.substring(mapping.match.from, mapping.match.to + 1)
        };
        return new Word(content);
    });
}

function processEntitiesAndWords(annotatedText: AnnotatedText, interval: Interval): Array<Entity | Word> {
    if (interval.isEmpty()) return [];
    const relevantMappings = annotatedText.positions.filter(mapping => mapping.match.from >= interval.from && mapping.match.to <= interval.to);

    const content = [] as Array<Entity | Word>
    visitArray(relevantMappings, {
        beforeFirst(item: AnnotationPosition): void {
            if (item.match.from > 0) {
                const words = getWordsAt(annotatedText, new Interval(0, item.match.from - 1));
                for (let word of words) {
                    content.push(word);
                }
            }
        }
        , visitItem(item: AnnotationPosition, next: AnnotationPosition | null): void {
            const words = getWordsAt(annotatedText, new Interval(item.match.from, item.match.to))
            const isEntity = item.subAnnotations != null && item.subAnnotations.length > 0;
            if (isEntity) {
                const entity = annotatedText.annotations[item.annotationId];
                content.push(new Entity(entity.content, words))
            } else {
                for (let word of words) {
                    content.push(word);
                }
            }
            if (next != null) {
                const words = getWordsAt(annotatedText, new Interval(item.match.to + 1, next.match.from - 1))
                for (let word of words) {
                    content.push(word);
                }
            }
        }
        , afterLast(item: AnnotationPosition): void {
            if (item.match.to < annotatedText.text.length - 1) {
                const words = getWordsAt(annotatedText, new Interval(item.match.to + 1, annotatedText.text.length - 1));
                for (let word of words) {
                    content.push(word);
                }
            }
        }
    })


    return content;
}


interface ArrayVisitor<T> {
    beforeFirst(item: T): void

    visitItem(item: T, next: T | null): void

    afterLast(item: T): void
}

function visitArray<T>(input: Array<T>, visitor: ArrayVisitor<T>) {
    for (let attr in input) {
        const i = Number(attr);
        const item = input[i];
        if (i == 0) {
            visitor.beforeFirst(item);
        }
        visitor.visitItem(item, i != input.length - 1 ? input[i + 1] : null);
        if (i == input.length - 1) {
            visitor.afterLast(item);
        }

    }
}

export function preprocessAnnotatedText(annotatedText: AnnotatedText): PreProcessedAnnotatedText {
    validateAnnotatedText(annotatedText);
    const content = [] as Array<TextUnit>;

    visitArray(annotatedText.queryMapping, {
        beforeFirst(item: QueryMapping): void {
            if (item.textIndex.from > 0) {
                const result = processEntitiesAndWords(annotatedText, new Interval(0, item.textIndex.from - 1));
                for (let item of result) {
                    content.push(item);
                }
            }
        },
        visitItem(item: QueryMapping, next: QueryMapping | null): void {
            const result = processEntitiesAndWords(annotatedText, new Interval(item.textIndex.from, item.textIndex.to));
            content.push(new QueryMatch([item.queryIndex.from, item.queryIndex.to], result));

            if (next != null) {
                const result = processEntitiesAndWords(annotatedText, new Interval(item.textIndex.to + 1, next.textIndex.from - 1));
                for (let item of result) {
                    content.push(item);
                }
            }
        },
        afterLast(item: QueryMapping): void {
            if (item.textIndex.to < annotatedText.text.length - 1) {
                const result = processEntitiesAndWords(annotatedText, new Interval(item.textIndex.to + 1, annotatedText.text.length - 1));
                for (let item of result) {
                    content.push(item);
                }
            }
        }
    });

    return new PreProcessedAnnotatedText(content);
}
