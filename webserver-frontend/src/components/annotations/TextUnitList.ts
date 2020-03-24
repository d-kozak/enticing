import * as  yup from "yup";
import {validateOrNull} from "../../entities/validationUtils";

export type TextUnit = Word | Entity | QueryMatch

export interface Interval {
    from: number,
    to: number
}

export const intervalSchema = yup.object({
    from: yup.number().min(0),
    to: yup.number().min(0)
})

export function isInterval(obj: Object): obj is Interval {
    const maybeInterval = obj as Interval;
    return typeof maybeInterval.from === "number" && typeof maybeInterval.to === "number";
}

export interface TextUnitList {
    content: Array<TextUnit>
}

export const textUnitListSchema = yup.object({
    content: yup.array(yup.object()).min(0)
});

export function isTextUnitList(obj: Object): obj is TextUnitList {
    return validateOrNull(textUnitListSchema, obj) !== null;
}

export interface Word {
    type: "word",
    indexes: Array<string>
}

export const wordSchema = yup.object({
    indexes: yup.array(yup.string().required()).required()
})

export function isStringArray(obj: any): obj is Array<string> {
    if (!Array.isArray(obj)) return false;
    for (let elem of obj) {
        if (typeof elem !== 'string') return false;
    }
    return true;
}

export function isWord(obj: Object): obj is Word {
    return isStringArray((obj as Word).indexes);
}

export interface Entity {
    type: "entity",
    attributes: Array<string>,
    entityClass: string,
    words: Array<Word>
}

export const entitySchema = yup.object({
    attributes: yup.array(yup.string().required()).required(),
    entityClass: yup.string().required(),
    words: yup.array(wordSchema).required()
});

export function isEntity(obj: Object): obj is Entity {
    const maybeEntity = obj as Entity;
    if (!isStringArray(maybeEntity.attributes)) return false;
    if (typeof maybeEntity.entityClass !== "string") return false;
    if (!Array.isArray(maybeEntity.words)) return false;
    return true;
}

export interface QueryMatch {
    type: "queryMatch",
    queryMatch: Interval,
    content: Array<Entity | Word>
}

export const queryMatchSchema = yup.object({
    queryIndex: intervalSchema,
    content: yup.array().required()
})

export function isQueryMatch(obj: Object): obj is QueryMatch {
    const maybeMatch = obj as QueryMatch;
    return Array.isArray(maybeMatch.content) && isInterval(maybeMatch.queryMatch);
}

export function parseNewAnnotatedText(input: object): TextUnitList | null {
    if (!isTextUnitList(input)) {
        console.error("could not parse " + JSON.stringify(input));
        return null
    }
    return input;
}


export const emptyTextUnitList: TextUnitList = {content: []};