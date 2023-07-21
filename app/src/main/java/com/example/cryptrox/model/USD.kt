package com.example.cryptrox.model


import com.google.gson.annotations.SerializedName

data class USD(
    @SerializedName("fully_diluted_market_cap")
    var fullyDilutedMarketCap: Double?,
    @SerializedName("last_updated")
    var lastUpdated: String?,
    @SerializedName("market_cap")
    var marketCap: Double?,
    @SerializedName("market_cap_dominance")
    var marketCapDominance: Double?,
    @SerializedName("percent_change_1h")
    var percentChange1h: Double?,
    @SerializedName("percent_change_24h")
    var percentChange24h: Double?,
    @SerializedName("percent_change_30d")
    var percentChange30d: Double?,
    @SerializedName("percent_change_60d")
    var percentChange60d: Double?,
    @SerializedName("percent_change_7d")
    var percentChange7d: Double?,
    @SerializedName("percent_change_90d")
    var percentChange90d: Double?,
    @SerializedName("price")
    var price: Double?,
    @SerializedName("tvl")
    var tvl: Double?,
    @SerializedName("volume_24h")
    var volume24h: Double?,
    @SerializedName("volume_change_24h")
    var volumeChange24h: Double?
)