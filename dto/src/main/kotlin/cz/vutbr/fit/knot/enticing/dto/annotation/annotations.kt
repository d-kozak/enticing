package cz.vutbr.fit.knot.enticing.dto.annotation

// todo find out how to configure IDEA to treat these annotations as TODO items
// not possible yet, see  https://youtrack.jetbrains.com/issue/IDEA-169745

/**
 * Denotes a region of code that is a potential performance bottleneck
 * and should be eventually tested for performance and rewritten if necessary
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.EXPRESSION, AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Repeatable
annotation class Speed(val message: String)

/**
 * Denotes a region of code that is not finished yet and requires more work
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.EXPRESSION, AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Repeatable
annotation class Incomplete(val message: String)

/**
 * Denotes a region of code that is not written well and should be refactored
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.EXPRESSION, AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Repeatable
annotation class Cleanup(val message: String)

/**
 * Denotes an idea for improvement or change
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.EXPRESSION, AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Repeatable
annotation class WhatIf(val message: String)

/**
 * Warning about something that could possibly go wrong
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.EXPRESSION, AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Repeatable
annotation class Warning(val message: String)

/**
 * Denotes a region of code that is there only temporarily until a specific feature is ready
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.EXPRESSION, AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Repeatable
annotation class Temporary(val message: String)



