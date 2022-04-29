package com.deneme.demowallet4.model

import com.google.gson.annotations.SerializedName

data class TokenBean(
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("expires_in")
    val expiresIn: Int?,
    @SerializedName("id_token")
    val idToken: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("scope")
    val scope: String?,
    @SerializedName("token_type")
    val tokenType: String
)