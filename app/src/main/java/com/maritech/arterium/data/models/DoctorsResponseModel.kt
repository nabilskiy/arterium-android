package com.maritech.arterium.data.models

import com.google.gson.annotations.SerializedName

data class DoctorsResponseModel(
        @SerializedName("data")
        val data:List<DoctorsModel>
)

data class DoctorsModel(
        @SerializedName("id")
        val id: Int,
        @SerializedName("role")
        val role: Int,
        @SerializedName("role_key")
        val roleKey: String,
        @SerializedName("login")
        val login: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("created_at")
        val dateCreated: Long,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("region_id")
        val regionId: String,
        @SerializedName("region")
        val region: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("institution_type")
        val institutionType: String,
        @SerializedName("drug_programs")
        val programs: List<DrugProgramModel>,
        @SerializedName("total_sold_count")
        val totalSold: Int,
        var selected:Boolean = false

)