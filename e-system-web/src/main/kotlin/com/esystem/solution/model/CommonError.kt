package com.esystem.solution.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CommonError(var code: Int,
                       var type: String,
                       var message: String,
                       @JsonProperty(value = "wrong_params")
                       var wrongParams: List<CommonWrongParam>)