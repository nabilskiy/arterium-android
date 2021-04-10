package com.maritech.arterium.data.models

import com.google.gson.annotations.SerializedName

data class RegionsResponseModel(
        @SerializedName("data")
        val data: List<RegionModel>
)

data class RegionModel(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
)