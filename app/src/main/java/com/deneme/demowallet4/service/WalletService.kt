package com.deneme.demowallet4.service

import com.alibaba.fastjson.JSONObject

interface WalletService {
    fun postToWalletServer(body: JSONObject)
    fun getAccessToken(clientId: String?, clientSecret: String?): String?
}