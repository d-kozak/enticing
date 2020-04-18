package cz.vutbr.fit.knot.enticing.management.managementservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User is not allowed to perform this operation")
class InsufficientRoleException(message: String) : RuntimeException(message)