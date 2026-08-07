package com.example.stockmap.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UpdateBinDto(
    @SerializedName("bin_id")
    val binId: Int?
)