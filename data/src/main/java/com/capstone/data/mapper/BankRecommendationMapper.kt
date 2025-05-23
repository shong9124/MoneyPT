package com.capstone.data.mapper

import com.capstone.data.model.recommend.bank.SendBankProductsRequestDTO
import com.capstone.domain.model.recommend.PostRecommendation

val PostRecommendation.toDTO
    get() = SendBankProductsRequestDTO(this.amount, this.propensity, this.term)