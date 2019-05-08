package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.UserCredentials
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/user")
class UserController(private val userService: EnticingUserService) {

    @PostMapping
    fun signup(@RequestBody @Valid user: UserCredentials) {
        userService.saveUser(user)
    }

    @PutMapping
    fun update(@RequestBody @Valid user: User) {
        userService.updateUser(user)
    }
}