package com.example.bienestapp.data

import com.example.bienestapp.data.Recomendacion
import com.example.bienestapp.data.Usuario

data class ActividadSugerida(
    val id: Long = 0,
    val usuario: Usuario,
    val recomendacion: Recomendacion,
    val fecha: String,
    val completada: Boolean
)
