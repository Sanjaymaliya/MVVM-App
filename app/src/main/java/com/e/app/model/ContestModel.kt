package com.e.app.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ContestModel  {
    @SerializedName("dateTime")
    @Expose
    var dateTime: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("winAmount")
    @Expose
    var winAmount: String? = null
    @SerializedName("map")
    @Expose
    var map: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null

}


