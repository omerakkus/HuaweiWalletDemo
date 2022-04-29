package com.deneme.demowallet4.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder

class JsonFileUtils(val context: Context) {
    private val dir = "walletdemo/"

    fun readFile(fileName: String): String? {
        val path: String =
            dir + fileName
        return getJsonFile(path, context)
    }

    fun getJsonFile(fileName: String?, context: Context): String? {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName!!)
                )
            )
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}