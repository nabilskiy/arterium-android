package com.maritech.arterium.data.models

import com.google.gson.annotations.SerializedName

data class CreateDoctorRequestModel(
        @SerializedName("phone")
        val phone: String,
        @SerializedName("name")
        val name:String,
        @SerializedName("gender")
        val gender:String,
        @SerializedName("region_id")
        val region:String,
        @SerializedName("city")
        val city:String,
        @SerializedName("institution_title")
        val institution:String,
        @SerializedName("agent_id")
        val agentId:String,
        @SerializedName("drug_program_ids")
        val programs: Set<Int>
)