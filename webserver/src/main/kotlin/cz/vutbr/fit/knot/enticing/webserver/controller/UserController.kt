package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.dto.ChangePasswordCredentials
import cz.vutbr.fit.knot.enticing.webserver.dto.CreateUserRequest
import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.UserCredentials
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettingsId
import cz.vutbr.fit.knot.enticing.webserver.entity.SelectedMetadata
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/user")
class UserController(private val userService: EnticingUserService) {

    private val log = LoggerFactory.getLogger(UserController::class.java)

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

    @GetMapping("/text-metadata/{id}")
    fun loadSelectedMetadata(@PathVariable id: SearchSettingsId): SelectedMetadata = userService.loadSelectedMetadata(id).also {
        log.info("loaded selected metadata $it for searchSettings with id $id")
    }

    @PostMapping("/text-metadata/{id}")
    fun saveSelectedMetadata(@PathVariable id: SearchSettingsId, @RequestBody @Valid metadata: SelectedMetadata) {
        userService.saveSelectedMetadata(metadata, id)
        log.info("updated metadata $metadata for searchSettings with id $id")
    }

    @PostMapping("/add")
    fun create(@RequestBody @Valid createUserRequest: CreateUserRequest) = userService.saveUser(createUserRequest)

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