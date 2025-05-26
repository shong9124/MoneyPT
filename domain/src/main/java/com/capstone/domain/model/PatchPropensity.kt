package com.capstone.domain.model

data class PatchPropensity(
    val data: DomainPropensityData? = null,
    val message: String = "성공",
    val statusCode: String = "200",
    val timeStamp: String = ""
) {
    companion object {
        fun default(): PatchPropensity = PatchPropensity()
    }
}
