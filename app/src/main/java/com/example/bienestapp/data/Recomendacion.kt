package com.example.bienestapp.data

data class Recomendacion(
    val id: Long = 0,
    val usuarioId: Long,
    val fecha: String,
    val mensaje: String,
    val titulo: String = "Recomendación personalizada",
    val descripcion: String = "Basada en tu estado de ánimo diario"
)
