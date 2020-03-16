package com.e.app.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class TypesDatum {

    @SerializedName("TypeID")
    @Expose
    var typeID: String? = null
    @SerializedName("GameID")
    @Expose
    var gameID: String? = null
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("FeaturedImage")
    @Expose
    var featuredImage: String? = null
    @SerializedName("IsTournament")
    @Expose
    var isTournament: String? = null
}