package com.esystem.solution.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CommonModel(@JsonProperty(value = "type")
                       var type: String,
                       @JsonProperty(value = "value")
                       var value: Any)