import {EnticingObject} from "./EnticingObject";
import * as  yup from "yup";
import {validateOrNull} from "../../entities/validationUtils";


export interface TextUnit {
    size(): number
}

export class Interval extends EnticingObject {
    constructor(public from: number, public to: number) {
        super();
    }

    isEmpty() {
        return this.from > this.to;
    }
}

export const intervalSchema = yup.object({
    from: yup.number().min(0),
    to: yup.number().min(0)
})

export function isInterval(obj: Object): obj is Interval {
    return validateOrNull(intervalSchema, obj) !== null;
}

export class NewAnnotatedText extends EnticingObject {

    constructor(public content: Array<TextUnit>) {
        super();
    }

    size(): number {
        return this.content.map(elem => elem.size()).reduce((left, right) => left + right, 0)
    }
}

export const newAnnotatedTextSchema = yup.object({
    content: yup.array(yup.object())
})

export function isNewAnnotatedText(obj: Object): obj is NewAnnotatedText {
    return validateOrNull(newAnnotatedTextSchema, obj) !== null;
}


export class Word extends EnticingObject implements TextUnit {

    constructor(public indexes: Array<string>) {
        super();
    }

    size(): number {
        return 1;
    }


}

export const wordSchema = yup.object({
    indexes: yup.array(yup.string().required()).required()
})

export function isWord(obj: Object): obj is Word {
    return validateOrNull(wordSchema, obj) !== null;
}

export class Entity extends EnticingObject implements TextUnit {
    constructor(public attributes: Array<string>, public entityClass: string, public words: Array<Word>) {
        super();
    }

    size(): number {
        return this.words.map(word => word.size()).reduce((left, right) => left + right, 0)
    }
}

export const entitySchema = yup.object({
    attributes: yup.array(yup.string().required()).required(),
    entityClass: yup.string().required(),
    words: yup.array(wordSchema).required()
});

export function isEntity(obj: Object): obj is Entity {
    return validateOrNull(entitySchema, obj) !== null;
}

export class QueryMatch extends EnticingObject implements TextUnit {
    constructor(public queryMatch: Interval, public content: Array<Entity | Word>) {
        super();
    }

    size(): number {
        return this.content.map(elem => elem.size()).reduce((left, right) => left + right, 0)
    }
}

export const queryMatchSchema = yup.object({
    queryIndex: intervalSchema,
    content: yup.array().required()
})

export function isQueryMatch(obj: Object): obj is QueryMatch {
    return validateOrNull(queryMatchSchema, obj) !== null;
}

export function parseNewAnnotatedText(input: object): NewAnnotatedText | null {
    const elements: Array<TextUnit> = []
    if (!isNewAnnotatedText(input)) {
        console.error("could not parse " + JSON.stringify(input))
        return null
    }
    for (let elem of input.content) {
        const parsed = parseElement(elem)
        if (parsed != null)
            elements.push(parsed)
    }
    return new NewAnnotatedText(elements)
}

function parseElement(elem: TextUnit): TextUnit | null {
    if (isWord(elem)) {
        return new Word(elem.indexes)
    } else if (isEntity(elem)) {
        const words = elem.words.map(word => new Word(word.indexes))
        return new Entity(elem.attributes, elem.entityClass, words)
    } else if (isQueryMatch(elem)) {
        const interval = new Interval(elem.queryMatch.from, elem.queryMatch.to);

        // @ts-ignore incorrect stuff is filtered out, but typescript cannot see it :X
        const content: Array<Word | Entity> = elem.content.map(elem => parseElement(elem))
            .filter(elem => elem != null)
            .filter(elem => isWord(elem!) || isEntity(elem!))
        return new QueryMatch(interval, content)
    } else {
        console.error("could not parse " + JSON.stringify(elem) + ", skipping");
        return null
    }
}

export const dummyText = new NewAnnotatedText([new Word(["ERROR"])])