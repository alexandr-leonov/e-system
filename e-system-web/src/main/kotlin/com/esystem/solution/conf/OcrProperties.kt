package com.esystem.solution.conf

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "ocr")
open class OcrProperties {
    lateinit var path: String
}