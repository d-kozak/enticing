package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.base.path}/search-settings")
class SearchSettingsController(private val searchSettingsRepository: SearchSettingsRepository, private val userService: EnticingUserService) {

    @GetMapping("/select/{id}")
    fun select(@PathVariable id: Long) {
        userService.selectSettings(id)
    }

    @GetMapping
    fun getAll() = searchSettingsRepository.findAll()

    @PostMapping
    fun create(@RequestBody searchSettings: SearchSettings) = searchSettingsRepository.save(searchSettings)

    @PutMapping
    fun update(@RequestBody searchSettings: SearchSettings) = searchSettingsRepository.save(searchSettings)

    @DeleteMapping
    fun delete(@RequestBody searchSettings: SearchSettings) = searchSettingsRepository.delete(searchSettings)
}