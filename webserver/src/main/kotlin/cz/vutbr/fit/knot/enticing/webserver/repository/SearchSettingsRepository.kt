package cz.vutbr.fit.knot.enticing.webserver.repository

import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import org.springframework.data.jpa.repository.JpaRepository

interface SearchSettingsRepository : JpaRepository<SearchSettings, Long> {
    fun findByDefaultIsTrue(): SearchSettings?
    fun findByPrivateIsFalse(): List<SearchSettings>
}