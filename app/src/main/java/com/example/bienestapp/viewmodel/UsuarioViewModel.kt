package com.example.bienestapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestapp.data.Usuario
import com.example.bienestapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios

    fun obtenerUsuarios() {
        viewModelScope.launch {
            try {
                val lista = RetrofitInstance.api.obtenerUsuarios()
                _usuarios.value = lista
            } catch (e: Exception) {
                println("Error al obtener usuarios: ${e.message}")
            }
        }
    }
}
