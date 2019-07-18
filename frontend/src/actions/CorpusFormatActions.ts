import {CorpusFormat} from "../entities/CorpusFormat";

// TODO add separate reducer or merge totally with SearchSettings

export const CORPUS_FORMAT_LOADED = "[CORPUS FORMAT] LOADED";


export interface CorpusFormatLoadedAction {
    type: typeof CORPUS_FORMAT_LOADED,
    id: number,
    format: CorpusFormat
}

export const corpusFormatLoadedAction = (id: number, format: CorpusFormat): CorpusFormatLoadedAction => ({
    type: CORPUS_FORMAT_LOADED,
    id,
    format
})