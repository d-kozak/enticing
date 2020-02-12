package cz.vutbr.fit.knot.enticing.management.managementservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll()
    }
}