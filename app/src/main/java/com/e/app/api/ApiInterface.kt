package com.e.app.api

import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {


    @FormUrlEncoded
    @POST("user/set_location")
    fun set_location(
        @Field("latitude") latitude: String,
        @Field("longitude") additional_charges: String
    ): Call<String>


}
