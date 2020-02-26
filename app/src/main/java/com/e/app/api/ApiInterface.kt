package com.e.app.api


import com.example.zestbrains.misateacher.model.*
import com.example.zestbrains.misateacher.restapi.WebConstant.TOKEN
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
