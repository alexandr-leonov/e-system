package com.esystem.solution.appender

import com.esystem.solution.model.TessLang
import reactor.core.publisher.Mono

interface TessAppender {

    fun doOcr(tessdataPath: String, lang: TessLang, pathToImage: String): Mono<String>

}