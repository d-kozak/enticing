package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandState
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandType
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.User
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CommandEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.CommandRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class BuildService(
        private val commandRepository: CommandRepository,
        private val userService: ManagementUserService
) {

    fun isFree(name: String) = !commandRepository.existsByTypeAndArguments(CommandType.BUILD, name)

    fun save(name: String, user: User) = commandRepository.save(
            CommandEntity(0, CommandType.BUILD, CommandState.FINISHED, name, user.toEntity(),
                    LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now())
    )
}