package com.maritech.arterium.data.models

import com.google.gson.annotations.SerializedName

data class AgentRequestModel(
        @SerializedName("phone")
        val phone: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("gender")
        val gender: String
) 