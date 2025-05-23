package com.capstone.data.model.recommend.bank

import com.google.gson.annotations.SerializedName

data class GetBankProductsResponseDTO(
    @SerializedName("data")
    val data: GetData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)