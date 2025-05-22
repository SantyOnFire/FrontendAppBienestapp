package com.example.bienestapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestapp.data.ActividadSugerida
import com.example.bienestapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActividadViewModel : ViewModel() {

    private val _actividades = MutableStateFlow<List<ActividadSugerida>>(emptyList())
    val actividades: StateFlow<List<ActividadSugerida>> = _actividades

    fun cargarActividades(usuarioId: Long = 1L) {
        viewModelScope.launch {
            try {
                val resultado = RetrofitInstance.api.listarActividadesPorUsuario(usuarioId)
                _actividades.value = resultado
            } catch (e: Exception) {
                println("Error al cargar actividades: ${e.message}")
            }
        }
    }
}
