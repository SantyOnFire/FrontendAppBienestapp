package com.example.bienestapp.data

data class UsuarioRegistro(
    val nombre: String,
    val email: String,
    val passwordHash: String,
    val fechaRegistro: String
)
