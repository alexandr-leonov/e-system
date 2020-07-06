package com.esystem.solution.appender.impl

import com.esystem.solution.model.TessLang
import com.esystem.solution.appender.TessAppender
import net.sourceforge.tess4j.Tesseract
import net.sourceforge.tess4j.TesseractException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.io.File


@Service
class TessAppenderImpl : TessAppender {

    override fun doOcr(tessdataPath: String, lang: TessLang, pathToImage: String): Mono<String> {
        val tesseract = Tesseract()
        tesseract.setLanguage(lang.name.toLowerCase())
        tesseract.setDatapath(tessdataPath);
        try {
            return Mono.just(tesseract.doOCR(File(pathToImage)))
        } catch (e: TesseractException) {
            throw e
        }
    }

}