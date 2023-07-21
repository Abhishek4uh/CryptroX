package com.example.cryptrox.model


import com.google.gson.annotations.SerializedName

data class Platform(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("symbol")
    var symbol: String?,
    @SerializedName("token_address")
    var tokenAddress: String?
)