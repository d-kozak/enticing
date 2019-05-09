package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.dto.ChangePasswordCredentials
import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.UserCredentials
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/user")
class UserController(private val userService: EnticingUserService) {

    @GetMapping
    fun get(): User? {
        return userService.currentUser
    }

    @GetMapping("/all")
    fun getAll(): List<User> = userService.getAllUsers()

    @PostMapping
    fun signup(@RequestBody @Valid user: UserCredentials) {
        userService.saveUser(user)
    }

    @PutMapping
    fun update(@RequestBody @Valid user: User) {
        userService.updateUser(user)
    }

    @PutMapping("/password")
    fun changePassword(@RequestBody @Valid changePasswordCredentials: ChangePasswordCredentials) {
        userService.changePassword(changePasswordCredentials)
    }

    @DeleteMapping("/{userId}")
    fun delete(@PathVariable userId: Long) {
        userService.deleteUser(userId)
    }
}