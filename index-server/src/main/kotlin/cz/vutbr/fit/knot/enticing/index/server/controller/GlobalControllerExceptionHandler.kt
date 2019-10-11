package cz.vutbr.fit.knot.enticing.index.server.controller

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompilerException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(EqlCompilerException::class)
    fun eqlCompilerException(exception: EqlCompilerException, response: HttpServletResponse) {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "foooo")
    }
}
