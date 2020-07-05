package com.esystem.solution

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.esystem.solution"])
open class StreamingStartApplication

fun main(args: Array<String>) {
    runApplication<StreamingStartApplication>(*args)
}