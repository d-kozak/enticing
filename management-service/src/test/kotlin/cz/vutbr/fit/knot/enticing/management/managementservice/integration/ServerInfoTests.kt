package cz.vutbr.fit.knot.enticing.management.managementservice.integration

import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import cz.vutbr.fit.knot.enticing.management.managementservice.apiBasePath
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.extractPaginatedItems
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerInfoRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.findByFullAddress
import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.AfterAll
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime


@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestPropertySource(locations = ["/config.properties"])
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServerInfoTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var serverRepository: ServerInfoRepository

    @Autowired
    lateinit var componentRepository: ComponentRepository

    private val serverInfo = ServerInfo(1, "athena10.fit.vutbr.cz", 12, 6_000, null)

    private val timestamp = LocalDateTime.now()
    private val componentOne = ComponentInfo(2, 1, 8080, ComponentType.WEBSERVER, timestamp)
    private val timestamp2 = LocalDateTime.now()
    private val componentTwo = ComponentInfo(3, 1, 5627, ComponentType.INDEX_SERVER, timestamp2)

    @BeforeAll
    fun `register server and component`() {

        mvc.perform(post("$apiBasePath/server")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StaticServerInfo("athena10.fit.vutbr.cz:8080", ComponentType.WEBSERVER, 12, 6_000, timestamp).toJson()))
                .andExpect(status().isOk)
                .andExpect(content().json(serverInfo.toJson()))


        mvc.perform(post("$apiBasePath/server")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StaticServerInfo("athena10.fit.vutbr.cz:5627", ComponentType.INDEX_SERVER, 12, 6_000, timestamp2).toJson()))
                .andExpect(status().isOk)
                .andExpect(content().json(serverInfo.toJson()))

        assertThat(serverRepository.findByAddress("athena10.fit.vutbr.cz")?.toServerInfo(null))
                .isEqualTo(serverInfo)


        assertThat(componentRepository.findByFullAddress("athena10.fit.vutbr.cz:8080")?.toComponentInfo())
                .isEqualTo(componentOne)

        assertThat(componentRepository.findByFullAddress("athena10.fit.vutbr.cz:5627")?.toComponentInfo())
                .isEqualTo(componentTwo)
    }

    @AfterAll
    fun `delete server`() {
        mvc.perform(delete("$apiBasePath/server/1"))
                .andExpect(status().isOk)

        assertThat(serverRepository.findByAddress("athena10.fit.vutbr.cz")).isNull()
        assertThat(componentRepository.findByFullAddress("athena10.fit.vutbr.cz:8080")).isNull()
    }

    @Test
    fun `one server should be returned`() {
        val servers = mvc.perform(get("$apiBasePath/server"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.content", hasSize<ServerInfo>(1)))
                .extractPaginatedItems<ServerInfo>()
        assertThat(servers).contains(serverInfo)
    }


    @Test
    fun `submit some heartbeats and view stats`() {
        val hb1 = HeartbeatDto("athena10.fit.vutbr.cz:8080", ComponentType.WEBSERVER,
                ServerStatus(123, 0.5, 0.8), LocalDateTime.now().minusSeconds(2))
        val hb2 = HeartbeatDto("athena10.fit.vutbr.cz:8080", ComponentType.WEBSERVER,
                ServerStatus(125, 0.4, 0.7), LocalDateTime.now().minusSeconds(1))
        val hb3 = HeartbeatDto("athena10.fit.vutbr.cz:5627", ComponentType.INDEX_SERVER,
                ServerStatus(250, 0.2, 0.1))
        val firstItems = mvc.perform(get("$apiBasePath/server/1/stats"))
                .andExpect(status().isOk)
                .extractPaginatedItems<ServerStatus>()
        assertThat(firstItems).isEmpty()

        mvc.perform(post("$apiBasePath/heartbeat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(hb1.toJson())
        ).andExpect(status().isOk)

        val secondTry = mvc.perform(get("$apiBasePath/server/1/stats"))
                .andExpect(status().isOk)
                .extractPaginatedItems<ServerStatus>()
        assertThat(secondTry).contains(hb1.status)

        mvc.perform(post("$apiBasePath/heartbeat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(hb2.toJson())
        ).andExpect(status().isOk)

        mvc.perform(post("$apiBasePath/heartbeat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(hb3.toJson())
        ).andExpect(status().isOk)

        val lastTry = mvc.perform(get("$apiBasePath/server/1/stats"))
                .andExpect(status().isOk)
                .extractPaginatedItems<ServerStatus>()
        assertThat(lastTry).contains(hb1.status, hb2.status, hb3.status)
    }

    @Test
    fun `server details should be accessible`() {
        mvc.perform(get("$apiBasePath/server/1"))
                .andExpect(status().isOk)
                .andExpect(content().json(serverInfo.toJson()))
    }

    @Test
    fun `two components should be available`() {
        val components = mvc.perform(get("$apiBasePath/server/1/component"))
                .andExpect(status().isOk)
                .extractPaginatedItems<ComponentInfo>()
        assertThat(components).contains(componentOne, componentTwo)
    }
}

