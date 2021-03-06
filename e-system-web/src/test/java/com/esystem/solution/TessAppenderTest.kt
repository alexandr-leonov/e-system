package com.esystem.solution

import com.esystem.solution.conf.OcrProperties
import com.esystem.solution.conf.StaticProperties
import com.esystem.solution.model.TessLang
import com.esystem.solution.appender.TessAppender
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [StreamingStartApplication::class])
class TessAppenderTest {

    @Autowired
    lateinit var tessAppender: TessAppender

    @Autowired
    lateinit var ocrProperties: OcrProperties

    @Autowired
    lateinit var staticProperties: StaticProperties

    @Test
    fun doOcr() {
        StepVerifier.create(tessAppender.doOcr(ocrProperties.path, TessLang.RUS, staticProperties.directory + "/001.jpg")
                .log("doOcr-result"))
                .expectSubscription()
                .expectNextMatches { it != null}
                .verifyComplete()
    }

}