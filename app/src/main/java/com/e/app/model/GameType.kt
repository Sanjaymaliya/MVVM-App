package com.e.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GameType  {
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("flag")
    @Expose
    var flag: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: Data? = null

}
