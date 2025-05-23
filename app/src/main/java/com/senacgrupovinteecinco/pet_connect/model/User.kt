package com.senacgrupovinteecinco.pet_connect.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val token: String // Certifique-se que este campo existe
)