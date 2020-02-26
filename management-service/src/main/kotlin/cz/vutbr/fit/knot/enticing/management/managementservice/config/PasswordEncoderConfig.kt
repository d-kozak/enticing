package cz.vutbr.fit.knot.enticing.management.managementservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordEncoderConfiguration {
    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder(11)
}