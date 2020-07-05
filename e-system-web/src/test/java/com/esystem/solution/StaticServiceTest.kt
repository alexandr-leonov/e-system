package com.esystem.solution

import com.esystem.solution.service.web.StaticService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.codec.multipart.FilePart
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.test.expectError
import java.io.File
import java.io.IOException

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [StreamingStartApplication::class])
class StaticServiceTest {

    // In test class you have to use only one default constructor without params!
    @Autowired
    lateinit var staticService: StaticService

    @Test
    fun getFile() {
        StepVerifier.create(staticService.getFile(File("not found"))
                .log())
                .expectSubscription()
                .expectError(IOException::class)
                .verify()
    }


    @Test
    fun uploadFile() {
        try {
            StepVerifier.create(staticService.uploadFile(object {} as FilePart, "link", "")
                    .onErrorResume { e ->
                        e is Exception
                        Mono.error(Throwable())
                    }
                    .log())
                    .expectSubscription()
                    .expectError(Throwable::class)
                    .verify()
        } catch (ignore: ClassCastException) {
            print(ignore.printStackTrace())
        }
    }
}