package com.esystem.solution.conf

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "server")
open class ApplicationProperties {

    lateinit var url: String

}