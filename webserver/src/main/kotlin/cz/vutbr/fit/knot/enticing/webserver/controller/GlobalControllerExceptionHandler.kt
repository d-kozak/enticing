package cz.vutbr.fit.knot.enticing.webserver.controller


import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompilerException
import cz.vutbr.fit.knot.enticing.webserver.exception.InvalidPasswordException
import cz.vutbr.fit.knot.enticing.webserver.exception.ValueNotUniqueException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConstrainViolation(e: Exception) {

    }

    @ExceptionHandler(EqlCompilerException::class)
    fun eqlCompilerException(exception: EqlCompilerException, response: HttpServletResponse) {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.message)
    }

    @ExceptionHandler(InvalidPasswordException::class)
    fun invalidPasswordException(exception: InvalidPasswordException, response: HttpServletResponse) {
        val messageMap = mapOf("errors" to listOf(mapOf("field" to "oldPassword", "defaultMessage" to exception.message)))
        response.sendError(HttpStatus.BAD_REQUEST.value(), messageMap.toJson())
    }

    @ExceptionHandler(ValueNotUniqueException::class)
    fun handleValueNotUnique(exception: ValueNotUniqueException, response: HttpServletResponse) {
        val messageMap = mapOf("errors" to listOf(mapOf("field" to exception.field, "defaultMessage" to exception.message)))
        response.sendError(HttpStatus.BAD_REQUEST.value(), messageMap.toJson())
    }
}