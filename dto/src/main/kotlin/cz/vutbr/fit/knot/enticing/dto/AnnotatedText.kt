package cz.vutbr.fit.knot.enticing.dto

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

/**
 * A piece of text and it's annotations
 */
data class AnnotatedText(

        @field:NotEmpty
        val text: String,
        /**
         * Annotation content, separated from annotation location to potentially save a bit of space
         */
        @field:Valid
        val annotations: Map<String, Annotation>,

        /**
         * Positions of all annotations
         */
        @field:Valid
        val positions: List<AnnotationPosition>,

        /**
         * Information how the query was matched by the text
         */
        @field:Valid
        val queryMapping: List<QueryMapping>
)


/**
 * Annotation, that is metadata that belongs to part of the text
 */
data class Annotation(
        @field:NotBlank
        val id: String,
        val content: Map<String, String>
)

/**
 * Interval of the text and it's annotations
 */
data class AnnotationPosition(
        /**
         * Id based on which to lookup the content of the annotation
         */
        @field:NotBlank
        val annotationId: String,
        /**
         * Interval that was matched
         */
        @field:Valid
        val match: MatchedRegion,

        /**
         * Currently used for the Entity -> Word relationship
         * Entity may consist of several words, each has it's own annotations.
         * We could send just a list of overlapping intervals and the parent-child relationship could be recomputed on the frontend,
         * but since we know it on the backend, because it is part of the internal representation used in index-lib,
         * why not send it directly.
         */
        @field:Valid
        val subAnnotations: List<AnnotationPosition> = emptyList()
) {
    constructor(annotationId: String, match: Pair<Int, Int>) : this(annotationId, MatchedRegion(match.first, match.second))
}

/**
 * Information about one mapping between a part of input query and a part of document
 */
data class QueryMapping(
        /**
         * This part of text
         */
        @field:Valid
        val textIndex: MatchedRegion,

        /**
         * Was matched by this part of query
         */
        @field:Valid
        val queryIndex: MatchedRegion
) {
    constructor(textIndex: Pair<Int, Int>, queryIndex: Pair<Int, Int>) : this(MatchedRegion(textIndex.first, textIndex.second), MatchedRegion(queryIndex.first, queryIndex.second))
}

/**
 * Described interval on the AnnotatedText
 */
data class MatchedRegion(
        /**
         * Where the interval starts
         */
        @field:PositiveOrZero
        val from: Int,
        /**
         * The size of the interval
         */
        @field:Positive
        val size: Int
) {
    /**
     * Where the interval ends, computed from the start and size
     */
    val to: Int
        get() = from + size
}