package com.e.app.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("TypesCount")
    @Expose
    var typesCount: String? = null
    @SerializedName("TypesData")
    @Expose
    var typesData: List<TypesDatum>? = null
}