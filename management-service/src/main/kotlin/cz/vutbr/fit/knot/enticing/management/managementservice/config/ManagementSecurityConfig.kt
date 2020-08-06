package cz.vutbr.fit.knot.enticing.management.managementservice.config

import cz.vutbr.fit.knot.enticing.management.managementservice.service.ManagementUserService
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
        val userService: ManagementUserService,
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
                .antMatchers(HttpMethod.POST, "$apiBasePath/command")
                .hasRole("PLATFORM_MAINTAINER")
                .antMatchers(HttpMethod.POST, "$apiBasePath/user")
                .permitAll()
                .antMatchers(HttpMethod.GET, "$apiBasePath/user/all")
                .permitAll()
                .antMatchers(HttpMethod.GET, "$apiBasePath/user/details/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "$apiBasePath/user/add")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "$apiBasePath/bug-report/**")
                .hasRole("PLATFORM_MAINTAINER")
                .antMatchers("/*",
                        "/static/**",
                        "$apiBasePath/dashboard/**",
                        "$apiBasePath/login/**",
                        "$apiBasePath/log/**",
                        "$apiBasePath/perf/**",
                        "$apiBasePath/heartbeat/**",
                        "$apiBasePath/server/**",
                        "$apiBasePath/bug-report/**",
                        "$apiBasePath/component/**",
                        "$apiBasePath/command/**"
                )
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

