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
        val jweSignPrivateKey = "privateKey"
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
