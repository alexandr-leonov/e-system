package com.esystem.solution.proxy

import com.esystem.solution.model.CommonError
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.ResourceProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.result.view.ViewResolver
import reactor.core.publisher.Mono
import java.util.*

@Component
@Order(-2)
class CommonResponseExceptionHandler @Autowired constructor(
        errorAttributes: ErrorAttributes,
        resourceProperties: ResourceProperties,
        applicationContext: ApplicationContext,
        viewResolversProvider: ObjectProvider<List<ViewResolver>>,
        serverCodecConfigurer: ServerCodecConfigurer) :
        AbstractErrorWebExceptionHandler(
                errorAttributes,
                resourceProperties,
                applicationContext
        ) {

    init {
        setViewResolvers(viewResolversProvider.getIfAvailable { emptyList() })
        setMessageWriters(serverCodecConfigurer.writers)
        setMessageReaders(serverCodecConfigurer.readers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all(), HandlerFunction { this.renderError(it) })
    }

    private fun renderError(request: ServerRequest): Mono<ServerResponse> {

        val errorPropertiesMap: Map<String, Any> = getErrorAttributes(request, false)

        val error = CommonError(errorPropertiesMap.get("status").toString().toInt(),
                errorPropertiesMap.get("error").toString(),
                errorPropertiesMap.get("message").toString(),
                Collections.emptyList());

        return CommonExchange.response(HttpStatus.valueOf(error.code), null, error)
    }
}