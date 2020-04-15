package cz.vutbr.fit.knot.enticing.management.managementservice.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional
@Service
class ManagementUserService : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}