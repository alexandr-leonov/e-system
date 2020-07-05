package com.esystem.solution.service.impl

import com.esystem.solution.conf.StaticProperties
import com.esystem.solution.model.CommonModel
import com.esystem.solution.service.web.StaticService
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
class StaticServiceImpl @Autowired constructor(private val staticProperties: StaticProperties) : StaticService {

    override fun getFile(file: File): Mono<Pair<InputStreamResource, String>> =
            if (file.exists()) {
                val extension: String = FilenameUtils.getExtension(file.name)
                val content = InputStreamResource(FileInputStream(file))
                Mono.just(Pair(content, extension.toLowerCase()))
            } else Mono.error(FileNotFoundException("File not found"))

    override fun uploadFile(filePart: FilePart, uri: String, paramName: String): Mono<CommonModel> {
        filePart.transferTo(File(staticProperties.directory + "/" + filePart.filename()))
        val resultLink = StringBuilder(uri)
                .append("?")
                .append(paramName)
                .append("=")
                .append(filePart.filename())
                .toString()
        return Mono.just(CommonModel("link", resultLink))
    }

}