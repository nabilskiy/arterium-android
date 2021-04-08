package com.maritech.arterium.data.models

import com.google.gson.annotations.SerializedName

data class AgentResponseModel(
        @SerializedName("data")
        val data:List<AgentModel>
)

data class AgentModel(
        @SerializedName("id")
        val id:Int,
        @SerializedName("role")
        val role:Int,
        @SerializedName("role_key")
        val roleKey:String,
        @SerializedName("login")
        val login:String,
        @SerializedName("gender")
        val gender:String,
        @SerializedName("created_at")
        val dateCreated:Long,
        @SerializedName("name")
        val name:String
)
