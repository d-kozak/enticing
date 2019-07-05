package cz.vutbr.fit.knot.enticing.dto.utils

data class MResult<T> internal constructor(val value: T) {

    val isSuccess get() = value !is Failure

    val isFailure get() = value is Failure


    fun getOrNull() = if (value !is Failure) value else null

    fun rethrowException(): Nothing = if (value is Failure) {
        throw value.throwable
    } else throw IllegalStateException("This result $this is not a failure, cannot throw")

    companion object {
        fun <T> success(value: T): MResult<T> = MResult(value)

        fun <T> failure(throwable: Throwable): MResult<T> = MResult(createFailure(throwable)) as MResult<T>

        fun <T> runCatching(block: () -> T): MResult<T> =
                try {
                    success(block())
                } catch (ex: Exception) {
                    failure(ex)
                }
    }

    internal data class Failure(val throwable: Throwable)
}

private fun createFailure(throwable: Throwable) = MResult.Failure(throwable) as Any