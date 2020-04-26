package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ChangePasswordCredentials
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CreateUserRequest
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.User
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.UserCredentials
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ManagementUserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/user")
class UserController(
        private val userService: ManagementUserService,
        loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger { }

    @GetMapping
    fun get(): User? = userService.getCurrentUser()

    @GetMapping("/details/{login}")
    fun getUserDetails(@PathVariable login: String): User? = userService.getUser(login)

    @GetMapping("/all")
    fun getAll(pageable: Pageable): Page<User> = userService.getUsers(pageable)

    @PostMapping
    fun signUp(@RequestBody @Valid user: UserCredentials) {
        userService.singUp(user)
    }

    @PostMapping("/add")
    fun create(@RequestBody @Valid createUserRequest: CreateUserRequest) = userService.createNewUser(createUserRequest)

    @PutMapping
    fun update(@RequestBody @Valid user: User) = userService.updateUser(user)


    @PutMapping("/password")
    fun changePassword(@RequestBody @Valid changePasswordCredentials: ChangePasswordCredentials) {
        userService.changePassword(changePasswordCredentials)
    }

    @DeleteMapping("/{login}")
    fun delete(@PathVariable login: String) {
        userService.deleteUser(login)
    }
}