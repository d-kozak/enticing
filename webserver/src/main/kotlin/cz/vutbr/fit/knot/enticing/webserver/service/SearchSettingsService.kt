package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.webserver.dto.ImportedSearchSettings
import cz.vutbr.fit.knot.enticing.webserver.dto.toEntity
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.exception.ValueNotUniqueException
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
@Incomplete("seems that there is no authorization check, but there should be one - only admins should be able to edit these")
class SearchSettingsService(logService: MeasuringLogService, private val searchSettingsRepository: SearchSettingsRepository, private val userRepository: UserRepository, private val indexServerConnector: IndexServerConnector) {

    val logger = logService.logger { }

    fun setDefault(id: Long) {
        val previousDefault = searchSettingsRepository.findByDefaultIsTrue()
        if (previousDefault != null) {
            previousDefault.default = false
            searchSettingsRepository.save(previousDefault)
        }
        val newDefault = searchSettingsRepository.findById(id).orElseThrow { IllegalArgumentException("Unknown id $id") }
        newDefault.default = true
        searchSettingsRepository.save(newDefault)
    }

    fun getAll(authentication: Authentication?): List<SearchSettings> {
        return if (authentication?.authorities?.contains(SimpleGrantedAuthority("ROLE_ADMIN")) == true)
            searchSettingsRepository.findAll()
        else searchSettingsRepository.findByPrivateIsFalse()
    }

    fun import(searchSettings: ImportedSearchSettings): SearchSettings {
        val res = searchSettingsRepository.save(searchSettings.toEntity())
        logger.info("Imported new settings $res")
        return res
    }

    fun create(searchSettings: SearchSettings): SearchSettings {
        if (searchSettingsRepository.existsByName(searchSettings.name))
            throw ValueNotUniqueException("name", "Name ${searchSettings.name} is already taken")
        return searchSettingsRepository.save(searchSettings)
    }

    fun update(searchSettings: SearchSettings) = searchSettingsRepository.save(searchSettings)

    fun delete(id: Long) {
        val searchSettings = searchSettingsRepository.findById(id).orElseThrow { IllegalArgumentException("No settings with id $id found") }
        userRepository.detachSettingsFromAllUsers(searchSettings)
        searchSettingsRepository.deleteById(id)
    }

    fun getStatus(id: Long): Map<String, String> {
        val settings = searchSettingsRepository.findById(id).orElseThrow { IllegalArgumentException("No settings with id $id found") }
        return settings.servers.associateWith { indexServerConnector.getStatus(it) }
    }
}