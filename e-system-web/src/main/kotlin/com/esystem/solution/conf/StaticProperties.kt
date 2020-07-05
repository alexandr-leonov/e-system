package com.esystem.solution.conf

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "static")
open class StaticProperties {

    lateinit var directory: String

    lateinit var extensions: Array<String>
}