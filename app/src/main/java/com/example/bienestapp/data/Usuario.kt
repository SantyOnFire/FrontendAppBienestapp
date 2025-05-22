package com.example.bienestapp.data

data class Usuario(
    val id: Long,
    val nombre: String,
    val email: String,
    val passwordHash: String,
    val fechaRegistro: String
)
