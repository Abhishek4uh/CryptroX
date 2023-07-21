package com.example.cryptrox.model



import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("circulating_supply")
    var circulatingSupply: Double?,
    @SerializedName("cmc_rank")
    var cmcRank: Int?,
    @SerializedName("date_added")
    var dateAdded: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("last_updated")
    var lastUpdated: String?,
    @SerializedName("max_supply")
    var maxSupply: Long?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("num_market_pairs")
    var numMarketPairs: Int?,
    @SerializedName("platform")
    var platform: Platform?,
    @SerializedName("quote")
    var quote: Quote?,
    @SerializedName("self_reported_circulating_supply")
    var selfReportedCirculatingSupply: Double?,
    @SerializedName("self_reported_market_cap")
    var selfReportedMarketCap: Double?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("symbol")
    var symbol: String?,
    @SerializedName("tags")
    var tags: List<String?>?,
    @SerializedName("total_supply")
    var totalSupply: Double?,
    @SerializedName("tvl_ratio")
    var tvlRatio: Double?
)