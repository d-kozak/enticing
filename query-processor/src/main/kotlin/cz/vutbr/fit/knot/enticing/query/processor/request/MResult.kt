package cz.vutbr.fit.knot.enticing.query.processor.request

data class MResult<T> internal constructor(val value: T) {

    val isSuccess get() = value !is Failure

    val isFailure get() = !isSuccess


    fun getOrNull() = if (value !is Failure) value else null


    companion object {
        fun <T> success(value: T) = MResult(value)

        fun failure(throwable: Throwable) = MResult(createFailure(throwable))
    }

    internal data class Failure(val throwable: Throwable)
}

private fun createFailure(throwable: Throwable) = MResult(MResult.Failure(throwable)) as Any