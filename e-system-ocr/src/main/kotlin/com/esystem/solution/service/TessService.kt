package com.esystem.solution.service

import com.esystem.solution.model.TessLang
import reactor.core.publisher.Mono

interface TessService {

    fun doOcr(tessdataPath: String, lang: TessLang, pathToImage: String): Mono<String>

}