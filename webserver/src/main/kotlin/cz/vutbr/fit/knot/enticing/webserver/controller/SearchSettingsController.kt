package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.dto.ImportedSearchSettings
import cz.vutbr.fit.knot.enticing.webserver.dto.toEntity
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/search-settings")
class SearchSettingsController(private val searchSettingsRepository: SearchSettingsRepository, private val userService: EnticingUserService, private val userRepository: UserRepository) {

    @GetMapping("/select/{id}")
    fun select(@PathVariable id: Long) {
        userService.selectSettings(id)
    }

    @PutMapping("/default/{id}")
    fun selectDefault(@PathVariable id: Long) {
        val previousDefault = searchSettingsRepository.findByDefaultIsTrue()
        if (previousDefault != null) {
            previousDefault.default = false
            searchSettingsRepository.save(previousDefault)
        }
        val newDefault = searchSettingsRepository.findById(id).orElseThrow { IllegalArgumentException("Unknown id $id") }
        newDefault.default = true
        searchSettingsRepository.save(newDefault)
    }

    @GetMapping
    fun getAll(authentication: Authentication?) = if (authentication?.authorities?.contains(SimpleGrantedAuthority("ROLE_ADMIN")) == true)
        searchSettingsRepository.findAll()
    else searchSettingsRepository.findByPrivateIsFalse()


    @PostMapping("/import")
    fun import(@RequestBody @Valid searchSettings: ImportedSearchSettings) = searchSettingsRepository.save(searchSettings.toEntity())

    @PostMapping
    fun create(@RequestBody @Valid searchSettings: SearchSettings) = searchSettingsRepository.save(searchSettings)

    @PutMapping
    fun update(@RequestBody @Valid searchSettings: SearchSettings) = searchSettingsRepository.save(searchSettings)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        val searchSettings = searchSettingsRepository.findById(id).orElseThrow { java.lang.IllegalArgumentException("No settings with id $id found") }
        userRepository.detachSettingsFromAllUsers(searchSettings)
        searchSettingsRepository.deleteById(id)

    }
}