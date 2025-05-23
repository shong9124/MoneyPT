package com.capstone.data.model.recommend.bank


import com.google.gson.annotations.SerializedName

data class SendBankProductsRequestDTO(
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("propensity")
    val propensity: String,
    @SerializedName("term")
    val term: Int
)