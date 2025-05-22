package com.example.bienestapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestapp.data.Usuario
import com.example.bienestapp.data.UsuarioRegistro
import com.example.bienestapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow("")
    val estado: StateFlow<String> = _estado

    fun registrarUsuario(
        nombre: String,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // ✅ Usamos UsuarioRegistro para evitar el error del ID
        val nuevoUsuario = UsuarioRegistro(
            nombre = nombre,
            email = email,
            passwordHash = password,
            fechaRegistro = fechaActual
        )

        viewModelScope.launch {
            try {
                // ✅  método debe ser suspend sin respuesta
                RetrofitInstance.api.registrarUsuario(nuevoUsuario)

                // 🔁 Busca el usuario por email para guardar el ID
                val usuarios = RetrofitInstance.api.obtenerUsuarios()
                val usuario = usuarios.find { it.email == email }

                if (usuario != null) {
                    val prefs = getApplication<Application>()
                        .getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
                    prefs.edit().putLong("usuarioId", usuario.id).apply()

                    _estado.value = "exito"
                    onSuccess()
                } else {
                    _estado.value = "error"
                }

            } catch (e: Exception) {
                _estado.value = "error"
            }
        }
    }
}
