package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.dto.ImportedSearchSettings
import cz.vutbr.fit.knot.enticing.webserver.dto.toEntity
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.exception.ValueNotUniqueException
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import javax.transaction.Transactional

private val log = LoggerFactory.getLogger(SearchSettingsService::class.java)

@Service
@Transactional
class SearchSettingsService(private val searchSettingsRepository: SearchSettingsRepository, private val userRepository: UserRepository) {

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

    fun import(searchSettings: ImportedSearchSettings) = searchSettingsRepository.save(searchSettings.toEntity())

    fun create(searchSettings: SearchSettings): SearchSettings {
        if (searchSettingsRepository.existsByName(searchSettings.name))
            throw ValueNotUniqueException("name", "Name ${searchSettings.name} is already taken")
        return searchSettingsRepository.save(searchSettings)
    }

    fun update(searchSettings: SearchSettings) = searchSettingsRepository.save(searchSettings)

    fun delete(id: Long) {
        val searchSettings = searchSettingsRepository.findById(id).orElseThrow { java.lang.IllegalArgumentException("No settings with id $id found") }
        userRepository.detachSettingsFromAllUsers(searchSettings)
        searchSettingsRepository.deleteById(id)
    }

}