package cz.vutbr.fit.knot.enticing.management.managementservice.integration

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LogDto
import cz.vutbr.fit.knot.enticing.management.managementservice.apiBasePath
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.extractPaginatedItems
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerInfoRepository
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestPropertySource(locations = ["/config.properties"])
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComponentTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var serverRepository: ServerInfoRepository

    @Autowired
    lateinit var componentRepository: ComponentRepository

    private val timestamp1 = LocalDateTime.now().minusDays(1)
    private val serverOne = ServerInfo(1, "athena10.fit.vutbr.cz", 12, 6_000, null)
    private val componentOne = ComponentInfo(2, 1, 8080, ComponentType.WEBSERVER, timestamp1)

    private val timestamp2 = LocalDateTime.now()
    private val componentTwo = ComponentInfo(3, 1, 5627, ComponentType.INDEX_SERVER, timestamp2)

    private val timestamp3 = LocalDateTime.now()
    private val serverTwo = ServerInfo(4, "knot01.fit.vutbr.cz", 11, 5555, null)
    private val componentThree = ComponentInfo(5, 4, 5627, ComponentType.INDEX_SERVER, timestamp3)


    @BeforeAll
    fun `three components on two servers`() {
        mvc.perform(post("$apiBasePath/server")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StaticServerInfo("athena10.fit.vutbr.cz:8080", ComponentType.WEBSERVER, 12, 6_000, timestamp1).toJson()))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(serverOne.toJson()))

        mvc.perform(post("$apiBasePath/server")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StaticServerInfo("athena10.fit.vutbr.cz:5627", ComponentType.INDEX_SERVER, 12, 6_000, timestamp2).toJson()))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(serverOne.toJson()))

        mvc.perform(post("$apiBasePath/server")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StaticServerInfo("knot01.fit.vutbr.cz:5627", ComponentType.INDEX_SERVER, 11, 5555, timestamp3).toJson()))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(serverTwo.toJson()))

    }

    @Test
    fun `three components should be available`() {
        val components = mvc.perform(get("$apiBasePath/component"))
                .andExpect(status().isOk)
                .extractPaginatedItems<ComponentInfo>()
        assertThat(components).containsExactlyInAnyOrder(componentOne, componentTwo, componentThree)
    }


    @Test
    fun `send some logs`() {
        val logOne = LogDto("foo.bar", "baz", LogType.WARN, "athena10.fit.vutbr.cz:5627", ComponentType.INDEX_SERVER, LocalDateTime.now().minusMinutes(2))
        val logTwo = LogDto("bar.baz", "bal", LogType.INFO, "athena10.fit.vutbr.cz:8080", ComponentType.WEBSERVER, LocalDateTime.now().minusMinutes(1))
        val logThree = LogDto("err.err", "err", LogType.ERROR, "athena10.fit.vutbr.cz:8080", ComponentType.WEBSERVER, LocalDateTime.now())

        var allLogs = mvc.perform(get("$apiBasePath/log"))
                .andExpect(status().isOk)
                .extractPaginatedItems<LogDto>()
        assertThat(allLogs).isEmpty()

        for (log in listOf(logOne, logTwo, logThree)) {
            mvc.perform(post("$apiBasePath/log")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(log.toJson()))
                    .andExpect(status().isOk)
        }

        allLogs = mvc.perform(get("$apiBasePath/log"))
                .andExpect(status().isOk)
                .extractPaginatedItems<LogDto>()
        assertThat(allLogs).containsExactlyInAnyOrder(logOne, logTwo, logThree)

        val infoLogs = mvc.perform(get("$apiBasePath/log")
                .param("logType", "INFO"))
                .andExpect(status().isOk)
                .extractPaginatedItems<LogDto>()
        assertThat(infoLogs).containsExactlyInAnyOrder(logTwo)

        val webserverLogs = mvc.perform(get("$apiBasePath/log")
                .param("componentType", "WEBSERVER"))
                .andExpect(status().isOk)
                .extractPaginatedItems<LogDto>()
        assertThat(webserverLogs).containsExactlyInAnyOrder(logTwo, logThree)

        val webserverErrorLogs = mvc.perform(get("$apiBasePath/log")
                .param("componentType", "WEBSERVER")
                .param("logType", "ERROR"))
                .andExpect(status().isOk)
                .extractPaginatedItems<LogDto>()
        assertThat(webserverErrorLogs).containsExactlyInAnyOrder(logThree)
    }
}