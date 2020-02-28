package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import org.apache.commons.dbcp.BasicDataSource
import org.springframework.context.annotation.*
import org.springframework.core.type.AnnotatedTypeMetadata
import java.net.URI

@Configuration
class DatabaseConfig(
        loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger { }

    @Bean
    @Conditional(DatabaseUrlIsSetCondition::class)
    fun dataSource(): BasicDataSource {
        val dbUri = URI(System.getenv("DATABASE_URL"))

        val (username, password) = dbUri.userInfo.split(":")

        val dbUrl = "jdbc:postgresql://${dbUri.host}:${dbUri.port}${dbUri.path}?sslmode=require"

        return BasicDataSource().apply {
            this.url = dbUrl
            this.username = username
            this.password = password
        }.also {
            logger.debug("Data source created successfully")
        }
    }

    class DatabaseUrlIsSetCondition : Condition {

        private val logger = org.slf4j.LoggerFactory.getLogger(DatabaseUrlIsSetCondition::class.java)

        override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean = (System.getenv("DATABASE_URL") != null).apply {
            if (!this) {
                logger.warn("DATABASE_URL is not set, in memory database will be used instead of postgres")
            }
        }
    }

}