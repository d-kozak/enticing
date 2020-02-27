package cz.vutbr.fit.knot.enticing.webserver.controller


import cz.vutbr.fit.knot.enticing.api.ComponentNotAccessibleException
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompilerException
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.util.error
import cz.vutbr.fit.knot.enticing.webserver.exception.InvalidPasswordException
import cz.vutbr.fit.knot.enticing.webserver.exception.InvalidSearchSettingsException
import cz.vutbr.fit.knot.enticing.webserver.exception.ValueNotUniqueException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class GlobalControllerExceptionHandler(logService: MeasuringLogService) {

    val logger = logService.logger { }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(e: Exception) {
        logger.error(e)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ComponentNotAccessibleException::class)
    fun componentNotAccessibleException(e: Exception) {
        logger.error(e)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConstrainViolation(e: Exception) {
        logger.error(e)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidSearchSettingsException::class)
    fun invalidSearchSettings(exception: InvalidSearchSettingsException) {
        logger.error(exception)
    }


    @ExceptionHandler(EqlCompilerException::class)
    fun eqlCompilerException(exception: EqlCompilerException, response: HttpServletResponse) {
        logger.error(exception)
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.message)
    }

    @ExceptionHandler(InvalidPasswordException::class)
    fun invalidPasswordException(exception: InvalidPasswordException, response: HttpServletResponse) {
        logger.error(exception)
        val messageMap = mapOf("errors" to listOf(mapOf("field" to "oldPassword", "defaultMessage" to exception.message)))
        response.sendError(HttpStatus.BAD_REQUEST.value(), messageMap.toJson())
    }

    @ExceptionHandler(ValueNotUniqueException::class)
    fun handleValueNotUnique(exception: ValueNotUniqueException, response: HttpServletResponse) {
        logger.error(exception)
        val messageMap = mapOf("errors" to listOf(mapOf("field" to exception.field, "defaultMessage" to exception.message)))
        response.sendError(HttpStatus.BAD_REQUEST.value(), messageMap.toJson())
    }
}