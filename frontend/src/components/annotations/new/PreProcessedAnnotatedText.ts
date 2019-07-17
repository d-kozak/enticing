import {EnticingObject} from "./EnticingObject";
import {
    AnnotatedText,
    Annotation,
    AnnotationPosition,
    MatchedRegion,
    QueryMapping,
    validateAnnotatedText
} from "../../../entities/Annotation";


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

    constructor(public content: ContentMap, public from: number, public to: number) {
        super();
    }
}

export class Entity extends EnticingObject {
    constructor(public attributes: ContentMap, public words: Array<Word>) {
        super();
    }
}

export class QueryMatch extends EnticingObject {
    constructor(public queryIndex: [number, number], public subunits: Array<Entity | Word>) {
        super();
    }
}

function getWordAnnotationPositions(text: AnnotatedText): Array<AnnotationPosition> {
    const result = [] as Array<AnnotationPosition>

    for (let position of text.positions) {
        if (position.subAnnotations === undefined || position.subAnnotations.length == 0) {
            result.push(position)
        } else {
            for (let subposition of position.subAnnotations) {
                result.push(subposition)
            }
        }
    }

    return result
}


function getWords(text: AnnotatedText): Array<Word> {
    const positions = getWordAnnotationPositions(text);
    if (positions.length == 0) {
        console.warn("AnnotationPositions should never be empty, no meta information can be shown like this")
    }
    return positions.filter(position => position.subAnnotations === undefined || position.subAnnotations.length == 0)
        .flatMap((position, index, array) => {
            const words = [] as Array<Word>
            if (index == 0 && position.match.from > 0) {
                const token = text.text.substring(0, position.match.from - 1);
                words.push(new Word({token}, 0, position.match.from - 1));
            }

            const {from, to} = position.match;
            const metadata = text.annotations[position.annotationId];
            const content = {
                ...metadata.content,
                token: text.text.substring(position.match.from, position.match.to)
            };
            const word = new Word(content, from, to);
            words.push(word);

            if (index < array.length - 1) {
                const next = array[index + 1];
                if (position.match.to + 1 < next.match.from) {
                    const token = text.text.substring(position.match.to + 1, next.match.from - 1);
                    words.push(new Word({token}, position.match.to + 1, next.match.from - 1))
                }
            }

            if (index == array.length - 1 && position.match.to < text.text.length - 1) {
                const token = text.text.substring(position.match.to + 1);
                words.push(new Word({token}, position.match.to + 1, text.text.length - 1));
            }

            return words;
        });
}

function addIndexes(words: Array<Word>, text: AnnotatedText) {
    function findCorrespondingIndexes(region: MatchedRegion) {
        const {from, to} = region;
        const fromIndex = words.findIndex(word => word.from <= from && word.to >= from);
        if (fromIndex === -1) {
            console.error('could not find corresponding fromIndex for word mapping ' + JSON.stringify(region, null, 2) + ' in ' + JSON.stringify(words, null, 2));
            return undefined;
        }
        const toIndex = words.findIndex(word => word.from <= to && word.to >= to);
        if (toIndex === -1) {
            console.error('could not find corresponding toIndex for word mapping ' + JSON.stringify(region, null, 2) + ' in ' + JSON.stringify(words, null, 2));
            return undefined;
        }
        return MatchedRegion.fromInterval(fromIndex, toIndex);
    }


    for (let mapping of text.queryMapping) {
        mapping.wordIndex = findCorrespondingIndexes(mapping.textIndex);
    }
    for (let position of text.positions) {
        position.wordIndex = findCorrespondingIndexes(position.match);
        if (position.subAnnotations) {
            for (let subposition of position.subAnnotations) {
                subposition.wordIndex = findCorrespondingIndexes(subposition.match);
            }
        }
    }
}


// todo cleanup even though this method accepts an array of query mappings, it only uses the first item of that array
const splitAnnotation = (position: AnnotationPosition, queryMapping: Array<QueryMapping>): Array<AnnotationPosition> => {
    const {annotationId, wordIndex} = position;
    const {from, to} = wordIndex!
    for (let mapping of queryMapping) {
        const index = mapping.wordIndex;
        if (!index) continue;
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


function processEntitiesAt(annotations: { [key: string]: Annotation }, words: Array<Word>, newPositions: AnnotationPosition[], interval: Interval): Array<Entity | Word> {
    const annotationPositions = newPositions.filter(position => position.subAnnotations !== undefined && position.subAnnotations.length > 0);
    if (annotationPositions.length == 0) {
        return words.slice(interval.from, interval.to + 1)
    }
    return annotationPositions
        .flatMap((position, index, array) => {
            if (!position.wordIndex) return [];
            const parts = [] as Array<Entity | Word>;
            if (index == 0 && position.wordIndex.from > 0) {
                words.slice(0, position.wordIndex.from)
                    .forEach(item => parts.push(item));
            }

            const metadata = annotations[position.annotationId];
            const entity = new Entity(metadata.content, words.slice(position.wordIndex.from, position.wordIndex.to + 1))
            parts.push(entity);
            if (index < array.length - 1) {
                const next = array[index + 1];
                if (next.wordIndex && position.wordIndex.to + 1 < next.wordIndex.from) {
                    words.slice(position.wordIndex.to + 1, next.wordIndex.from)
                        .forEach(item => parts.push(item))
                }
            }


            if (index == array.length - 1 && position.wordIndex.to < words.length - 1) {
                words.slice(position.wordIndex.to)
                    .forEach(item => parts.push(item));
            }

            return parts;
        });
}

export function preprocessAnnotatedText(annotatedText: AnnotatedText): PreProcessedAnnotatedText {
    validateAnnotatedText(annotatedText);

    const words = getWords(annotatedText);
    addIndexes(words, annotatedText);

    const newPositions = annotatedText.positions.flatMap(position => splitAnnotation(position, annotatedText.queryMapping));


    if (annotatedText.queryMapping.length === 0) {
        const text = processEntitiesAt(annotatedText.annotations, words, newPositions, new Interval(0, words.length - 1));
        return new PreProcessedAnnotatedText(text);
    }

    const result = annotatedText.queryMapping.flatMap((mapping, index) => {
        if (!mapping.wordIndex) return [] as Array<TextUnit>;
        const parts = [] as Array<TextUnit>
        if (index === 0 && mapping.wordIndex.from > 0) {
            const prefix = processEntitiesAt(annotatedText.annotations, words, newPositions, new Interval(0, mapping.wordIndex.from - 1));
            prefix.forEach(item => parts.push(item))
        }
        const match = new QueryMatch([mapping.textIndex.from, mapping.textIndex.to], processEntitiesAt(annotatedText.annotations, words, newPositions, new Interval(mapping.wordIndex.from, mapping.wordIndex.to)))
        parts.push(match);

        if (index < annotatedText.queryMapping.length - 1) {
            const next = annotatedText.queryMapping[index + 1];
            if (next.wordIndex && mapping.wordIndex.to + 1 < next.wordIndex!.from) {
                const middle = processEntitiesAt(annotatedText.annotations, words, newPositions, new Interval(mapping.wordIndex.to + 1, next.wordIndex.from));
                middle.forEach(item => parts.push(item));
            }
        }

        if (index == annotatedText.queryMapping.length - 1 && mapping.wordIndex.to < words.length - 1) {
            const suffix = processEntitiesAt(annotatedText.annotations, words, newPositions, new Interval(mapping.wordIndex.to + 1, words.length - 1));
            suffix.forEach(item => parts.push(item));
        }
        return parts;
    });


    return new PreProcessedAnnotatedText(result);
}


