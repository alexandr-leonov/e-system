package com.esystem.solution.handler

import com.esystem.solution.appender.file.StaticService
import com.esystem.solution.conf.StaticProperties
import com.esystem.solution.model.CommonError
import com.esystem.solution.model.CommonWrongParam
import com.esystem.solution.proxy.CommonExchange
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.codec.multipart.Part
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.io.File
import java.io.IOException
import java.util.*

@Component
class StaticHandler @Autowired constructor(val staticProperties: StaticProperties,
                                           val staticService: StaticService) : CommonHandler() {

    private val PARAMETER_NAME = "filename"

    /*
     * POST /static
     * */
    fun uploadFile(request: ServerRequest): Mono<ServerResponse> = request.body(BodyExtractors.toMultipartData())
            .flatMap { parts ->
                val map: Map<String, Part> = parts.toSingleValueMap()
                val filePart: FilePart = map.get(PARAMETER_NAME) as FilePart

                return@flatMap if (!checkFileExtension(filePart.filename()))
                    errorResponse(CommonError(HttpStatus.BAD_REQUEST.value(),
                            "Неверный параметр запроса",
                            PARAMETER_NAME + " задан некорректно",
                            Collections.singletonList(CommonWrongParam(PARAMETER_NAME,
                                    "Некорректное расширение файла",
                                    PARAMETER_NAME + " задан некорректно"))))
                else staticService.uploadFile(staticProperties.directory, filePart, request.exchange().getRequest().getURI().toString(), PARAMETER_NAME)
                        .log("upload-file-response")
                        .flatMap(this::successResponse)
            }

    /*
    * GET /static
    * */
    fun getFile(request: ServerRequest): Mono<ServerResponse> =
            if (request.queryParam(PARAMETER_NAME).isPresent && request.queryParam(PARAMETER_NAME).get().isNotEmpty()) {
                val file = File(staticProperties.directory + "/" + request.queryParam(PARAMETER_NAME).get())

                staticService.getFile(file)
                        .flatMap { contentAndExtensionPair ->
                            ServerResponse.ok()
                                    .contentType(MediaType.valueOf("image/" + contentAndExtensionPair.second))
                                    .body(BodyInserters.fromObject(contentAndExtensionPair.first))
                        }
                        .log("get-file-response")
                        .onErrorResume { e ->
                            e is IOException
                            errorResponse(CommonError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                    "Ошибка при загрузке файла",
                                    e.message.orEmpty(),
                                    Collections.emptyList()))
                        }
            } else CommonExchange.response(HttpStatus.BAD_REQUEST, null, CommonError(
                    HttpStatus.BAD_REQUEST.value(),
                    "Параметр не задан",
                    "Параметр " + PARAMETER_NAME + " не задан.",
                    Collections.emptyList()))


    private fun checkFileExtension(filename: String): Boolean {
        val currentExtension: String = FilenameUtils.getExtension(filename)
        staticProperties.extensions.forEach {
            if (it.toLowerCase() == currentExtension.toLowerCase()) return true
        }
        return false
    }

}