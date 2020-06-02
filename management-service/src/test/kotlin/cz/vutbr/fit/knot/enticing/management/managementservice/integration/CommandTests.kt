package cz.vutbr.fit.knot.enticing.management.managementservice.integration

import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.management.managementservice.apiBasePath
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandRequest
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandType
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CommandDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.UserEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.CommandRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestPropertySource(locations = ["/config.properties"])
@AutoConfigureMockMvc
@WhatIf("integration tests here trigger")
@Disabled
class CommandTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var commandRepository: CommandRepository

    @BeforeEach
    fun `add admin`() {
        userRepository.save(UserEntity("admin", encoder.encode("admin12"), true, mutableSetOf("PLATFORM_MAINTAINER", "ADMIN")))
    }

    @AfterEach
    fun `delete admin`() {
        userRepository.deleteById("admin")
    }

    @Test
    @WithMockUser(username = "admin", roles = ["PLATFORM_MAINTAINER", "ADMIN"])
    fun `start new index server`() {
        assertThat(commandRepository.findAll()).isEmpty()
        val command = mvc.perform(post("$apiBasePath/command")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CommandRequest(CommandType.START_INDEX_SERVER, "knot01.fit.vutbr.cz:5555").toJson()))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
                .toDto<CommandDto>()
        println(command)

        commandRepository.deleteAll()
    }
}