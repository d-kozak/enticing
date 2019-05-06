package cz.vutbr.fit.knot.enticing.webserver.config

import org.apache.commons.dbcp.BasicDataSource
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.*
import org.springframework.core.type.AnnotatedTypeMetadata
import java.net.URI

@Configuration
class DatabaseConfig {
    private val logger = LoggerFactory.getLogger(DatabaseConfig::class.java)

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
        private val logger = LoggerFactory.getLogger(DatabaseUrlIsSetCondition::class.java)

        override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean = (System.getenv("DATABASE_URL") != null).apply {
            if (!this) {
                logger.warn("DATABASE_URL is not set, in memory database will be used instead of postgres")
            }
        }
    }

}