package com.esystem.solution.appender.file.impl

import com.esystem.solution.model.CommonModel
import com.esystem.solution.appender.file.StaticService
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

@Service
class StaticServiceImpl : StaticService {

    override fun getFile(file: File): Mono<Pair<InputStreamResource, String>> =
            if (file.exists()) {
                val extension: String = FilenameUtils.getExtension(file.name)
                val content = InputStreamResource(FileInputStream(file))
                Mono.just(Pair(content, extension.toLowerCase()))
            } else Mono.error(FileNotFoundException("File not found"))

    override fun uploadFile(directory: String, filePart: FilePart, uri: String, paramName: String): Mono<CommonModel> {
        filePart.transferTo(File(directory + "/" + filePart.filename()))
        val resultLink = StringBuilder(uri)
                .append("?")
                .append(paramName)
                .append("=")
                .append(filePart.filename())
                .toString()
        return Mono.just(CommonModel("link", resultLink))
    }

}