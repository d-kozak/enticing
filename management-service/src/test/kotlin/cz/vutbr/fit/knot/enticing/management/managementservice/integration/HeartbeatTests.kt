package cz.vutbr.fit.knot.enticing.management.managementservice.integration

import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerInfoRepository
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

private val api = "/api/v1"

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestPropertySource(locations = ["/config.properties"])
@AutoConfigureMockMvc
class HeartbeatTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var server: ServerInfoRepository

    @Test
    fun `server is added to the database`() {
        val serverInfo = StaticServerInfo("athena10.fit.vutbr.cz:8080", ComponentType.WEBSERVER, 12, 6_000)
        mvc.perform(post("$api/server")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serverInfo.toJson()))
                .andExpect(status().isOk)
                .andExpect(content().json(ServerInfo(1, "athena10.fit.vutbr.cz", 12, 6_000, null).toJson()))
    }
}