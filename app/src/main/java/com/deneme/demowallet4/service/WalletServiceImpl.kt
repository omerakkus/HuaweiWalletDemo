package com.deneme.demowallet4.service

import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.deneme.demowallet4.model.TokenBean
import retrofit2.Call
import retrofit2.Callback

class WalletServiceImpl(var context: Context) : WalletService {

    val prefences = context.getSharedPreferences("demoWallet", Context.MODE_PRIVATE)
    val editor = prefences.edit()

    override fun postToWalletServer(
        body: JSONObject
    ){
        val clientId = "105745651"
        val clientSecret = "4b464165157e00e0f04475929fbc46616ec39dd6ad65947bc973c98f8768a458"

        val accessToken = getAccessToken(clientId, clientSecret)
        val authorization = "Bearer $accessToken"

        val apiInterface = FlightService.create()
            .createFlightModel(authorization,"application/json;charset=utf-8",body)

        apiInterface.enqueue(object : Callback<JSONObject?> {
            override fun onResponse(
                call: Call<JSONObject?>,
                response: retrofit2.Response<JSONObject?>
            ) {
                if(response.isSuccessful){
                    Log.d("DemoWallet",response.body().toString())
                }
            }
            override fun onFailure(call: Call<JSONObject?>, t: Throwable) {
                Log.e("DemoWallet",t.message.toString())
            }
        })
    }

    override fun getAccessToken(clientId: String?, clientSecret: String?): String {

        val apiInterface = AccessTokenService.create()
            .getAccessToken("client_credentials",clientId,clientSecret)

        apiInterface.enqueue(object : Callback<TokenBean?> {
            override fun onResponse(
                call: Call<TokenBean?>,
                response: retrofit2.Response<TokenBean?>
            ){
                if(response.isSuccessful){
                    editor.putString("access_token",response.body()?.accessToken.toString())
                    editor.commit()
                }
            }
            override fun onFailure(call: Call<TokenBean?>, t: Throwable) {
                Log.e("DemoWallet", t.message.toString())
            }

        })
        return prefences.getString("access_token","").toString()
    }
}