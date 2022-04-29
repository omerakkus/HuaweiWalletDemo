package com.deneme.demowallet4

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.alibaba.fastjson.JSONObject
import com.deneme.demowallet4.jwe.JweUtil
import com.deneme.demowallet4.service.WalletServiceImpl
import com.deneme.demowallet4.utils.JsonFileUtils

class MainActivity : AppCompatActivity() {
    private lateinit var walletServiceImpl: WalletServiceImpl
    private lateinit var btnAddBoardingPass: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAddBoardingPass = findViewById(R.id.add_pass)

        walletServiceImpl = WalletServiceImpl(applicationContext)

        val newModel = JSONObject.parseObject(JsonFileUtils(applicationContext).readFile("FlightModel.json"))
        val newInstance  = JSONObject.parseObject(JsonFileUtils(applicationContext).readFile("FlightInstance.json"))

        btnAddBoardingPass.setOnClickListener {
            createBoardingModel(newModel)
            createBoardingInstance(newInstance)
        }

    }

    private fun createBoardingModel(json: JSONObject) {
        walletServiceImpl.postToWalletServer(json)
    }

    private fun createBoardingInstance(newInstance : JSONObject) {
        val appId = "105745651"
        newInstance["iss"] = appId
        val payload = newInstance.toJSONString()
        val jweSignPrivateKey = "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"
        val jwe= JweUtil.generateJwe(jweSignPrivateKey, payload)
        Log.d("WalletJwe", jwe)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("wallet://com.huawei.wallet/walletkit/consumer/pass/save?content=$jwe")
        try{
            startActivity(intent)
        }catch (e: ActivityNotFoundException){
            Log.e("WalletJwe", e.toString())
        }
    }
}