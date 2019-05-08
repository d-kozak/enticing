package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.dto.UserCredentials
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/user")
class UserController(private val userService: EnticingUserService) {

    @PostMapping
    fun signup(@RequestBody @Valid user: UserCredentials) {
        userService.saveUser(user)
    }
}