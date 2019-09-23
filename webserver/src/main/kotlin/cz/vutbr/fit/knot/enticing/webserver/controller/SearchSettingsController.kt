package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.dto.ImportedSearchSettings
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import cz.vutbr.fit.knot.enticing.webserver.service.SearchSettingsService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/search-settings")
class SearchSettingsController(private val searchSettingsService: SearchSettingsService, private val userService: EnticingUserService) {

    @GetMapping("/select/{id}")
    fun select(@PathVariable id: Long) {
        userService.selectSettings(id)
    }

    @PutMapping("/default/{id}")
    fun selectDefault(@PathVariable id: Long) = searchSettingsService.setDefault(id)

    @GetMapping
    fun getAll(authentication: Authentication?) = searchSettingsService.getAll(authentication)

    @PostMapping("/import")
    fun import(@RequestBody @Valid searchSettings: ImportedSearchSettings) = searchSettingsService.import(searchSettings)

    @PostMapping
    fun create(@RequestBody @Valid searchSettings: SearchSettings): SearchSettings = searchSettingsService.create(searchSettings)

    @PutMapping
    fun update(@RequestBody @Valid searchSettings: SearchSettings) = searchSettingsService.update(searchSettings)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        searchSettingsService.delete(id)
    }
}