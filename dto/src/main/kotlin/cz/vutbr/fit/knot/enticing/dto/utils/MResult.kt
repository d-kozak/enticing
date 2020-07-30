package cz.vutbr.fit.knot.enticing.dto.utils

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup


/**
 * Temporary substitute for @see Result from kotlin standard lib, which is still experimental
 */
@Cleanup("use kotlin's real Result type when possible")
data class MResult<T> internal constructor(val value: T) {

    val isSuccess get() = value !is Failure

    val isFailure get() = value is Failure


    fun getOrNull() = if (value !is Failure) value else null

    fun rethrowException(): Nothing = throw exception

    fun unwrap(): T = if (isFailure) rethrowException() else this.value

    val exception: Throwable
        get() = if (value is Failure) {
            value.throwable
        } else throw UnsupportedOperationException("This result $this is not a failure, cannot throw")

    companion object {
        fun <T> success(value: T): MResult<T> = MResult(value)

        fun <T> failure(throwable: Throwable): MResult<T> = MResult(createFailure(throwable)) as MResult<T>

        inline fun <T> runCatching(block: () -> T): MResult<T> =
                try {
                    success(block())
                } catch (ex: Exception) {
                    failure(ex)
                }
    }

    internal data class Failure(val throwable: Throwable)
}

private fun createFailure(throwable: Throwable) = MResult.Failure(throwable) as Any