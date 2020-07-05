package com.esystem.solution.conf

import com.esystem.solution.handler.StaticHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok

@EnableWebFlux
@Configuration
@EnableConfigurationProperties
open class WebConfig : WebFluxConfigurer {

    companion object {
        val CLASSPATH_RESOURCE_LOCATIONS = arrayOf("classpath:/", "classpath:/frontend/")
    }
    @Autowired
    private val applicationProperties: ApplicationProperties? = null

    @Bean
    open fun staticRoute(staticHandler: StaticHandler): RouterFunction<ServerResponse> {
        val customUrl = "/static"
        return route()
                .GET(applicationProperties!!.url + customUrl, HandlerFunction { staticHandler.getFile(it) })
                .POST(applicationProperties!!.url + customUrl, { staticHandler.uploadFile(it) })
                .build()
    }

    /*
     * root routing to swagger
     */

    @Bean
    open fun htmlRouter(@Value("classpath:/frontend/swagger.html") html: Resource): RouterFunction<ServerResponse> {
        return route(GET(applicationProperties!!.url + "/"), HandlerFunction { request -> ok().contentType(MediaType.TEXT_HTML).syncBody(html) })
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry!!.addResourceHandler(applicationProperties!!.url + "/**")
                .addResourceLocations(*CLASSPATH_RESOURCE_LOCATIONS)
    }

}