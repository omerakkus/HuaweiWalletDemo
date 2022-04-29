package com.deneme.demowallet4.service

import com.alibaba.fastjson.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FlightService {

    @POST("flight/model")
    fun createFlightModel(
        @Header("Authorization") authorization: String?,
        @Header("Content-Type") contentType: String?,
        @Body jsonObject: JSONObject
    ): Call<JSONObject?>

    companion object {
        private var BASE_URL = "https://wallet-passentrust-dre.cloud.huawei.eu/hmspass/v1/" //For Europe region

        fun create() : FlightService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(FlightService::class.java)

        }
    }
}