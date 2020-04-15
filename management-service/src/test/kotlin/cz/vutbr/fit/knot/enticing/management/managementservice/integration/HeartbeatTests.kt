package cz.vutbr.fit.knot.enticing.management.managementservice.integration

import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerInfoRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.findByFullAddress
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
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

private val api = "/api/v1"

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestPropertySource(locations = ["/config.properties"])
@AutoConfigureMockMvc
class HeartbeatTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var serverRepository: ServerInfoRepository

    @Autowired
    lateinit var componentRepository: ComponentRepository

    @Test
    fun `server is added to the database`() {
        val timestamp = LocalDateTime.now()
        val serverInfo = ServerInfo(1, "athena10.fit.vutbr.cz", 12, 6_000, null)
        mvc.perform(post("$api/server")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StaticServerInfo("athena10.fit.vutbr.cz:8080", ComponentType.WEBSERVER, 12, 6_000, timestamp).toJson()))
                .andExpect(status().isOk)
                .andExpect(content().json(serverInfo.toJson()))

        assertThat(serverRepository.findByAddress("athena10.fit.vutbr.cz")?.toServerInfo(null))
                .isEqualTo(serverInfo)

        assertThat(componentRepository.findByFullAddress("athena10.fit.vutbr.cz:8080")?.toComponentInfo())
                .isEqualTo(ComponentInfo(2, 1, 8080, ComponentType.WEBSERVER, timestamp))

        mvc.perform(delete("$api/server/1"))
                .andExpect(status().isOk)

        assertThat(serverRepository.findByAddress("athena10.fit.vutbr.cz")).isNull()
        assertThat(componentRepository.findByFullAddress("athena10.fit.vutbr.cz:8080")).isNull()
    }

    @Test
    fun `by default no servers should be returned`() {
        mvc.perform(get("$api/server"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.content", hasSize<ServerInfo>(0)))
    }

}