package cz.vutbr.fit.knot.enticing.management.managementservice.integration

import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.management.managementservice.apiBasePath
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ChangePasswordCredentials
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CreateUserRequest
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.User
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.UserCredentials
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.UserEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestPropertySource(locations = ["/config.properties"])
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `user info not accessible when not logged in`() {
        mvc.perform(get("$apiBasePath/user"))
                .andExpect(status().`is`(401))
    }

    @Test
    @WithMockUser(username = "honza", roles = ["USER"])
    fun `user info returned for logged in user`() {
        mvc.perform(get("$apiBasePath/user"))
                .andExpect(status().isOk)
                .andExpect(content().json(User("honza", roles = setOf("USER")).toJson()))
    }

    @Test
    fun `register user`() {
        assertThat(userRepository.findAll()).isEmpty()
        mvc.perform(post("$apiBasePath/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserCredentials("voldemort", "unicorn").toJson()))
                .andExpect(status().isOk)
        val users = userRepository.findAll()
        assertThat(users).hasSize(1)
        val voldemort = users[0]
        assertThat(voldemort.login).isEqualTo("voldemort")
        assertThat(voldemort.roles).isEmpty()
        assertThat(encoder.matches("unicorn", voldemort.encryptedPassword)).isTrue()

        userRepository.deleteAll()
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `create new user as admin`() {
        assertThat(userRepository.findAll()).isEmpty()
        mvc.perform(post("$apiBasePath/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CreateUserRequest("hagrid", "tesak02", roles = setOf("klicnik", "safar")).toJson()))
                .andExpect(status().isOk)
        val users = userRepository.findAll()
        assertThat(users).hasSize(1)
        val hagrid = users[0]
        assertThat(hagrid.login).isEqualTo("hagrid")
        assertThat(hagrid.roles).containsExactlyInAnyOrder("klicnik", "safar")
        assertThat(encoder.matches("tesak02", hagrid.encryptedPassword)).isTrue()
        userRepository.deleteAll()
    }

    @Test
    @WithMockUser(username = "lojza", roles = [])
    fun `cannot create new user if not admin`() {
        mvc.perform(post("$apiBasePath/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CreateUserRequest("hagrid", "tesak02", roles = setOf("klicnik", "safar")).toJson()))
                .andExpect(status().`is`(403))
    }

    @Test
    @WithMockUser("adam01")
    fun `user can delete itself`() {
        val adam = UserEntity("adam01", encoder.encode("foo"))
        userRepository.save(adam)
        mvc.perform(delete("$apiBasePath/user/adam01"))
                .andExpect(status().isOk)
        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    @WithMockUser("lojza")
    fun `user cannot delete others`() {
        val adam = UserEntity("adam01", encoder.encode("foo"))
        userRepository.save(adam)
        mvc.perform(delete("$apiBasePath/user/adam01"))
                .andExpect(status().`is`(403))
        assertThat(userRepository.findAll()).contains(adam)
        userRepository.deleteAll()
    }

    @Test
    @WithMockUser("admin", roles = ["ADMIN"])
    fun `admin can delete others`() {
        val adam = UserEntity("adam01", encoder.encode("foo"))
        userRepository.save(adam)
        mvc.perform(delete("$apiBasePath/user/adam01"))
                .andExpect(status().isOk)
        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    @WithMockUser("adam01")
    fun `user can change his password`() {
        val adam = UserEntity("adam01", encoder.encode("foo123"))
        userRepository.save(adam)
        mvc.perform(put("$apiBasePath/user/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ChangePasswordCredentials("adam01", "foo123", "bar123").toJson()))
                .andExpect(status().isOk)
        val updatedAdam = userRepository.findByIdOrNull("adam01") ?: fail("user should still be in the database")
        assertThat(encoder.matches("bar123", updatedAdam.encryptedPassword))
        userRepository.deleteAll()
    }


    @Test
    @WithMockUser("admin", roles = ["ADMIN"])
    fun `admin can change anyones password`() {
        val adam = UserEntity("adam01", encoder.encode("foo123"))
        userRepository.save(adam)
        mvc.perform(put("$apiBasePath/user/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ChangePasswordCredentials("adam01", "foo123", "bar123").toJson()))
                .andExpect(status().isOk)
        val updatedAdam = userRepository.findByIdOrNull("adam01") ?: fail("user should still be in the database")
        assertThat(encoder.matches("bar123", updatedAdam.encryptedPassword))
        userRepository.deleteAll()
    }

    @Test
    @WithMockUser("lojza")
    fun `use cant change password of other users`() {
        val adam = UserEntity("adam01", encoder.encode("foo123"))
        userRepository.save(adam)
        mvc.perform(put("$apiBasePath/user/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ChangePasswordCredentials("adam01", "foo123", "bar123").toJson()))
                .andExpect(status().`is`(403))
        val stillSameAdam = userRepository.findByIdOrNull("adam01") ?: fail("user should still be in the database")
        assertThat(encoder.matches("foo123", stillSameAdam.encryptedPassword))
        userRepository.deleteAll()
    }

    @Test
    @WithMockUser("admin", roles = ["ADMIN"])
    fun `admin can assign roles`() {
        val adam = UserEntity("adam01", encoder.encode("foo123"))
        userRepository.save(adam)
        mvc.perform(put("$apiBasePath/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(User("adam01", setOf("BIG_BOSS")).toJson()))
                .andExpect(status().isOk)
        val updatedAdam = userRepository.findByIdOrNull("adam01") ?: fail("user should still be in the database")
        assertThat(updatedAdam.roles).containsExactlyInAnyOrder("BIG_BOSS")
        userRepository.deleteAll()
    }

    @Test
    @WithMockUser("adam01")
    fun `user cant assign roles`() {
        val adam = UserEntity("adam01", encoder.encode("foo123"))
        userRepository.save(adam)
        mvc.perform(put("$apiBasePath/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(User("adam01", setOf("BIG_BOSS")).toJson()))
                .andExpect(status().isOk)
        val updatedAdam = userRepository.findByIdOrNull("adam01") ?: fail("user should still be in the database")
        assertThat(updatedAdam.roles).isEmpty()
        userRepository.deleteAll()
    }
}