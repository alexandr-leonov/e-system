package com.esystem.solution.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CommonResponse(@JsonProperty(value = "data")
                          var data: Any?,
                          @JsonProperty(value = "error")
                          var error: CommonError?)