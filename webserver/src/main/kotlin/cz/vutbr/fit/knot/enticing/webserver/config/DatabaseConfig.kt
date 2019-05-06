package cz.vutbr.fit.knot.enticing.webserver.config

import org.apache.commons.dbcp.BasicDataSource
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI

@Configuration
class DatabaseConfig {
    private val logger = LoggerFactory.getLogger(DatabaseConfig::class.java)

    @Bean
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
}