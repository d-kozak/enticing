package cz.vutbr.fit.knot.enticing.index.server.controller

import cz.vutbr.fit.knot.enticing.api.ComponentNotAccessibleException
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompilerException
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.error
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcherException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletResponse


/**
 * Translates exceptions to HTTP codes
 */
@ControllerAdvice
class GlobalControllerExceptionHandler(loggerFactory: LoggerFactory) {

    val logger = loggerFactory.logger { }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(QueryDispatcherException::class)
    fun queryDispatcherException(e: Exception) {
        logger.error(e)
    }

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

    @ExceptionHandler(EqlCompilerException::class)
    fun eqlCompilerException(exception: EqlCompilerException, response: HttpServletResponse) {
        logger.error(exception)
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.message)
    }
}
