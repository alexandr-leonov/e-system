package com.esystem.solution.appender.file

import com.esystem.solution.model.CommonModel
import org.springframework.core.io.InputStreamResource
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Mono
import java.io.File

interface StaticService {

    fun uploadFile(directory: String, filePart: FilePart, uri: String, paramName:String): Mono<CommonModel>

    fun getFile(file: File): Mono<Pair<InputStreamResource, String>>

}