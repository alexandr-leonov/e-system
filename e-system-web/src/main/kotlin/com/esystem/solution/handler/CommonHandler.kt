package com.esystem.solution.handler

import com.esystem.solution.model.CommonError
import com.esystem.solution.proxy.CommonExchange
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

abstract class CommonHandler : CommonExchange {

    fun successResponse(body: Any) : Mono<ServerResponse> = CommonExchange.response(HttpStatus.OK, body, null)

    fun errorResponse(error: CommonError) : Mono<ServerResponse> = CommonExchange.response(HttpStatus.BAD_REQUEST, null, error)

}