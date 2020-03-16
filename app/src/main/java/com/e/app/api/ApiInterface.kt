package com.e.app.api

import com.e.app.model.GameType
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {


    @FormUrlEncoded
    @POST("Game/Fetch")
    fun gameFetch(
        @Field("Action") Action: String,
        @Field("Val_Userid") Val_UserId: String,
        @Field("Val_Gameid") Val_GameId: String
    ): Call<GameType>


}
