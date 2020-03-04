package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class SecurityConfig(
        val userService: EnticingUserService,
        @Value("\${api.base.path}") val apiBasePath: String
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("$apiBasePath/user/all")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "$apiBasePath/search-settings", "$apiBasePath/search-settings/import", "$apiBasePath/user/add")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "$apiBasePath/search-settings", "$apiBasePath/search-settings/default/*")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "$apiBasePath/search-settings")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "$apiBasePath/user")
                .permitAll()
                .antMatchers("/*",
                        "/static/**",
                        "$apiBasePath/login",
                        "$apiBasePath/search-settings",
                        "$apiBasePath/query",
                        "$apiBasePath/query/**",
                        "$apiBasePath/user/default-metadata/*",
                        "$apiBasePath/compiler",
                        "$apiBasePath/server-status")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("$apiBasePath/login")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("$apiBasePath/logout")
                .logoutSuccessHandler(HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService)
    }

    @Bean
    fun authenticationEntryPoint() = AuthenticationEntryPoint { _, response, _ ->
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized")
    }

    @Bean
    fun authenticationSuccessHandler() = object : SimpleUrlAuthenticationSuccessHandler() {
        override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse?, authentication: Authentication) {
            clearAuthenticationAttributes(request)
            redirectStrategy.sendRedirect(request, response, "$apiBasePath/user")
        }
    }

    @Bean
    fun authenticationFailureHandler() = SimpleUrlAuthenticationFailureHandler()
}