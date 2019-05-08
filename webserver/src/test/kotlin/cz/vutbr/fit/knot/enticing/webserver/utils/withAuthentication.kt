package cz.vutbr.fit.knot.enticing.webserver.utils

import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder


inline fun withAuthentication(userEntity: UserEntity, block: () -> Unit) {
    val authentication: Authentication = UsernamePasswordAuthenticationToken(userEntity, "foo")
    assertThat(SecurityContextHolder.getContext().authentication).isNull()
    SecurityContextHolder.getContext().authentication = authentication
    try {
        block()
    } finally {
        SecurityContextHolder.getContext().authentication = null
    }
}