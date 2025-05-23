package com.senacgrupovinteecinco.pet_connect.model

import com.google.gson.annotations.SerializedName

data class Local(
    @SerializedName("id") val id: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("endereco") val endereco: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("telefone") val telefone: String?,
    @SerializedName("imagem_url") val imagemUrl: String?
)