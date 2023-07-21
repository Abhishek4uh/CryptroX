package com.example.cryptrox.model


import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("USD")
    var uSD: USD?
)