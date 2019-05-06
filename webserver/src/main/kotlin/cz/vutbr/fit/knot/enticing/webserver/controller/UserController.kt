package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.entity.EnticingUser
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.base.path}/user")
class UserController(private val userService: EnticingUserService) {

    @GetMapping
    fun get() = "user"

    @PostMapping
    fun createUser(@RequestBody user: EnticingUser) {
        userService.saveUser(user)
    }
}