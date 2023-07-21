package com.example.cryptrox.model


import com.google.gson.annotations.SerializedName

data class ParentModelClass(
    @SerializedName("data")
    var `data`: List<Data?>?
)