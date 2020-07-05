package com.esystem.solution.model

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface CommonExchange {
    companion object {
        fun response(status: HttpStatus, body: Any?, error: CommonError?): Mono<ServerResponse> =
                ServerResponse.status(status).contentType(APPLICATION_JSON).syncBody(CommonResponse(body, error))
    }
}