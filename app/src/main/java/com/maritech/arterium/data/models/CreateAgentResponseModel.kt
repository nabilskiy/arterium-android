package com.maritech.arterium.data.models

import com.google.gson.annotations.SerializedName

data class CreateAgentResponseModel(
        @SerializedName("data")
        val data: AgentModel,
        @SerializedName("error")
        val error: String
)
