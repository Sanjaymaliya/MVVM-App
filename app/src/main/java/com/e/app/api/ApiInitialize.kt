package com.e.app.api


import com.e.app.BuildConfig.SERVICE_ENDPOINT
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiInitialize {

    private var retrofit: Retrofit? = null
    private lateinit var apiInIt: ApiInterface

    @Synchronized
    fun getApiInIt(): ApiInterface {
        return apiInIt
    }

    fun initialize(): Retrofit {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        if (ApiInitialize.retrofit == null) {
            ApiInitialize.retrofit = Retrofit.Builder()
                .baseUrl(SERVICE_ENDPOINT)
                .client(ApiInitialize.requestHeader)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        return ApiInitialize.retrofit!!
    }

    @JvmStatic
    fun initializes(): ApiInterface {

        val gson = GsonBuilder()
            .setLenient()
            .create()


        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_ENDPOINT)
            .client(requestHeader)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiInIt = retrofit!!.create(ApiInterface::class.java)
        return apiInIt
    }

    @JvmStatic
    fun initialize(baseUrl: String): ApiInterface {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(requestHeader)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiInIt = retrofit!!.create(ApiInterface::class.java)
        return apiInIt
    }


    private val requestHeader: OkHttpClient
        get() {

            return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
        }

}
