package cz.vutbr.fit.knot.enticing.webserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.PathResourceResolver


@Configuration
class WebConfig : WebMvcConfigurer {

    @Value("\${api.base.path}")
    lateinit var baseApiPath: String

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        // All resources go to where they should go
        registry
                .addResourceHandler("/**/*.css", "/**/*.html", "/**/*.js", "/**/*.jsx", "/**/*.png", "/**/*.ttf", "/**/*.woff", "/**/*.woff2")
                .setCachePeriod(0)
                .addResourceLocations("classpath:/static/")


        registry.addResourceHandler("/", "/**")
                .setCachePeriod(0)
                .addResourceLocations("classpath:/static/index.html")
                .resourceChain(true)
                .addResolver(object : PathResourceResolver() {
                    override fun getResource(resourcePath: String, location: Resource): Resource? {
                        if (resourcePath.startsWith(baseApiPath) || resourcePath.startsWith(baseApiPath.substring(1))) {
                            return null
                        }
                        return if (location.exists() && location.isReadable) location else null
                    }
                })
    }
}