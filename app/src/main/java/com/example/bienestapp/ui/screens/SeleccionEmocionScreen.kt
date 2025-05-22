package com.example.bienestapp.ui.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bienestapp.viewmodel.EmocionViewModel
import kotlinx.coroutines.delay

@Composable
fun SeleccionEmocionScreen(
    navController: NavController,
    viewModel: EmocionViewModel = viewModel()
) {
    val emociones = listOf(
        "😄" to "Felicidad",
        "😐" to "Neutral",
        "😢" to "Tristeza",
        "😠" to "Ira",
        "😰" to "Ansiedad"
    )

    var emocionSeleccionada by remember { mutableStateOf("") }
    val emocionSeleccionadaState = rememberUpdatedState(emocionSeleccionada)
    val estado by viewModel.estado.collectAsState()

    LaunchedEffect(estado) {
        if (estado == "registrada" && emocionSeleccionadaState.value.isNotBlank()) {
            Log.d("UI", "Navegando al test con emoción: ${emocionSeleccionadaState.value}")
            delay(300)
            navController.navigate("test/${emocionSeleccionadaState.value}")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF1C1C1C))
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¿Cómo te sientes hoy?",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(emociones) { (emoji, nombre) ->
                    var isPressed by remember { mutableStateOf(false) }
                    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .scale(scale)
                            .clickable {
                                isPressed = true
                                emocionSeleccionada = nombre
                                viewModel.registrarEmocion(nombre, usuarioId = 5L)
                            },
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2E2E2E),
                            contentColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = emoji, fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = nombre, fontSize = 16.sp, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (estado) {
                "error" -> Text("❌ Error al registrar la emoción", color = MaterialTheme.colorScheme.error)
                "registrada" -> Text("✅ Emoción registrada", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
