package cz.vutbr.fit.knot.enticing.management.managementservice.integration

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LogDto
import cz.vutbr.fit.knot.enticing.log.PerfDto
import cz.vutbr.fit.knot.enticing.management.managementservice.apiBasePath
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.GeneralOperationStatistics
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.extractPaginatedItems
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.LogRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.PerfRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerInfoRepository
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestPropertySource(locations = ["/config.properties"])
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
class ComponentTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var serverRepository: ServerInfoRepository

    @Autowired
    lateinit var componentRepository: ComponentRepository

    @Autowired
    lateinit var logRepository: LogRepository

    @Autowired
    lateinit var perfRepository: PerfRepository

    private val timestamp1 = LocalDateTime.now().minusDays(1)
    private var serverOne = ServerInfo(1, "athena10.fit.vutbr.cz", 12, 6_000, null)
    private var componentOne = ComponentInfo(2, 1, "athena10.fit.vutbr.cz", 8080, ComponentType.WEBSERVER, timestamp1)

    private val timestamp2 = LocalDateTime.now()
    private var componentTwo = ComponentInfo(3, 1, "athena10.fit.vutbr.cz", 5627, ComponentType.INDEX_SERVER, timestamp2)

    private val timestamp3 = LocalDateTime.now()
    private var serverTwo = ServerInfo(4, "knot01.fit.vutbr.cz", 11, 5555, null)
    private var componentThree = ComponentInfo(5, 4, "knot01.fit.vutbr.cz", 5627, ComponentType.INDEX_SERVER, timestamp3)


    @BeforeAll
    fun `three components on two servers`() {
        var res = mvc.perform(post("$apiBasePath/server")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StaticServerInfo("athena10.fit.vutbr.cz:8080", ComponentType.WEBSERVER, 12, 6_000, timestamp1).toJson()))
                .andExpect(status().isOk)
                .andReturn().response.contentAsString.toDto<ServerInfo>()
        serverOne = serverOne.copy(res.id)
        componentOne = componentOne.copy(id = res.id + 1, serverId = res.id)
        componentTwo = componentTwo.copy(id = res.id + 2, serverId = res.id)
        assertThat(res).isEqualTo(serverOne)

        mvc.perform(post("$apiBasePath/server")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StaticServerInfo("athena10.fit.vutbr.cz:5627", ComponentType.INDEX_SERVER, 12, 6_000, timestamp2).toJson()))
                .andExpect(status().isOk)
                .andExpect(content().json(serverOne.toJson()))

        res = mvc.perform(post("$apiBasePath/server")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StaticServerInfo("knot01.fit.vutbr.cz:5627", ComponentType.INDEX_SERVER, 11, 5555, timestamp3).toJson()))
                .andExpect(status().isOk)
                .andReturn().response.contentAsString.toDto<ServerInfo>()
        serverTwo = serverTwo.copy(res.id)
        componentThree = componentThree.copy(id = res.id + 1, serverId = res.id)
        assertThat(res).isEqualTo(serverTwo)
    }

    @AfterAll
    fun afterAll() {
        logRepository.deleteAll()
        perfRepository.deleteAll()
        componentRepository.deleteAll()
        serverRepository.deleteAll()
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


    @Test
    fun `perf logs`() {
        val perfOne = PerfDto("classOne", "operationOne", "args",
                100L, "SUCCESS", "athena10.fit.vutbr.cz:5627",
                ComponentType.INDEX_SERVER, LocalDateTime.now())
        val perfTwo = PerfDto("classOne", "operationOne", "args",
                200L, "SUCCESS", "athena10.fit.vutbr.cz:5627",
                ComponentType.INDEX_SERVER, LocalDateTime.now())
        val perfThree = PerfDto("classOne", "operationTwo", "args",
                300L, "SUCCESS", "athena10.fit.vutbr.cz:5627",
                ComponentType.INDEX_SERVER, LocalDateTime.now())

        val otherPerf = PerfDto("classTwo", "operationOne", "args",
                300L, "SUCCESS", "athena10.fit.vutbr.cz:8080",
                ComponentType.WEBSERVER, LocalDateTime.now())

        for (log in listOf(perfOne, perfTwo, perfThree, otherPerf)) {
            mvc.perform(post("$apiBasePath/perf")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(log.toJson()))
                    .andExpect(status().isOk)
        }

        val allLogs = mvc.perform(get("$apiBasePath/perf"))
                .andExpect(status().isOk)
                .extractPaginatedItems<PerfDto>()
        assertThat(allLogs).containsExactlyInAnyOrder(perfOne, perfTwo, perfThree, otherPerf)

        val opOneLogs = mvc.perform(get("$apiBasePath/perf")
                .param("operationId", "operationOne"))
                .andExpect(status().isOk)
                .extractPaginatedItems<PerfDto>()
        assertThat(opOneLogs).containsExactlyInAnyOrder(perfOne, perfTwo, otherPerf)

        val opOneStats = GeneralOperationStatistics("operationOne", 200.0, 8.16496580927726, 300, 100, 3)
        val opTwoStats = GeneralOperationStatistics("operationTwo", 300.0, 0.0, 300, 300, 1)
        mvc.perform(get("$apiBasePath/perf/stats"))
                .andExpect(status().isOk)
                .andExpect(content().json(mapOf(
                        "operationOne" to opOneStats,
                        "operationTwo" to opTwoStats
                ).toJson()))

        mvc.perform(get("$apiBasePath/perf/stats")
                .param("operationId", "operationOne"))
                .andExpect(status().isOk)
                .andExpect(content().json(opOneStats.toJson()))
        mvc.perform(get("$apiBasePath/perf/stats")
                .param("operationId", "operationTwo"))
                .andExpect(status().isOk)
                .andExpect(content().json(opTwoStats.toJson()))

    }
}