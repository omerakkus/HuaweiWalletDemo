package com.deneme.demowallet4.service

import com.deneme.demowallet4.model.TokenBean
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface AccessTokenService {

    @FormUrlEncoded
    @POST("token?grant_type=client_credentials&client_id=[CLIENT_ID]&client_secret=[CLIENT_SECRET]")
    fun getAccessToken(
        @Field("grant_type") grant_type: String?,
        @Field("client_id") client_id: String?,
        @Field("client_secret") client_secret: String?
    ): Call<TokenBean?>

    companion object {
        private var BASE_URL = "https://oauth-login.cloud.huawei.com/oauth2/v3/"

        fun create() : AccessTokenService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(AccessTokenService::class.java)
        }
    }
}