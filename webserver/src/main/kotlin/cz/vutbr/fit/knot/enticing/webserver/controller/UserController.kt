package cz.vutbr.fit.knot.enticing.webserver.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.base.path}/user")
class UserController {

    @GetMapping
    fun get() = "user"
}