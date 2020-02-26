package com.e.app.api

import com.google.gson.annotations.SerializedName


public class ApiError {
    @SerializedName("status")
    var status: Int? = null

    @SerializedName("message")
    var message: String? = null



}
