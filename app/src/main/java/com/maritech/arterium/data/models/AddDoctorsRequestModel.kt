package com.maritech.arterium.data.models

import com.google.gson.annotations.SerializedName

data class AddDoctorsRequestModel(
        @SerializedName("doctor_ids")
        val ids:List<Int>
)
