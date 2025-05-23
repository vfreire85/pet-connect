package com.senacgrupovinteecinco.pet_connect.model

import com.google.gson.annotations.SerializedName

data class CadastroResponse(
    @SerializedName("sucesso") val success: Boolean,
    @SerializedName("mensagem") val message: String,
    @SerializedName("id_cadastrado") val id: Int?
)