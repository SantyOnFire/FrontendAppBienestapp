package com.example.bienestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActividadesScreen(navController: NavController) {

    val emociones = listOf("Felicidad", "Neutral", "Tristeza", "Ira", "Ansiedad")

    val recomendacionesPorEmocion = mapOf(
        "Felicidad" to listOf(
            "Comparte tu día con alguien cercano",
            "Haz una lista de lo que te hizo sonreír",
            "Haz una foto de algo que te inspire",
            "Regálate tu comida o bebida favorita",
            "Haz algo amable por otra persona"
        ),
        "Neutral" to listOf(
            "Tómate una pausa consciente",
            "Ordena una parte pequeña de tu espacio",
            "Escribe una meta pequeña para hoy",
            "Escucha música suave",
            "Haz algo que llevas tiempo posponiendo"
        ),
        "Tristeza" to listOf(
            "Mira una película reconfortante",
            "Escribe lo que sientes",
            "Abraza algo que te dé calma",
            "Sal al sol y respira profundo",
            "Habla con alguien que te escuche"
        ),
        "Ira" to listOf(
            "Camina o haz ejercicio suave",
            "Escribe tu molestia y luego rompe el papel",
            "Respira profundo con calma",
            "Escucha música instrumental intensa",
            "Aléjate un momento del lugar"
        ),
        "Ansiedad" to listOf(
            "Haz respiración 4-4-4",
            "Cuenta 5 cosas que ves",
            "Dibuja o garabatea sin pensar",
            "Lava tu cara o manos con agua fría",
            "Imagina tu lugar seguro"
        )
    )

    val frasesPorRecomendacion = recomendacionesPorEmocion.values.flatten().associateWith {
        "💡 ${it.replaceFirstChar { c -> c.uppercase() }}"
    }

    var emocionSeleccionada by remember { mutableStateOf<String?>(null) }
    var fraseSeleccionada by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bienestar diario", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    text = "Actividades sugeridas",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Selecciona cómo te sientes para recibir recomendaciones personalizadas.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            items(emociones) { emocion ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                        .background(Color(0xFF2B2B2B), RoundedCornerShape(12.dp))
                        .clickable {
                            emocionSeleccionada = emocion
                            fraseSeleccionada = null
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = emocion,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            emocionSeleccionada?.let { emocion ->
                val recomendaciones = recomendacionesPorEmocion[emocion] ?: emptyList()
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "📋 Lista de recomendaciones para $emocion",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                recomendaciones.forEachIndexed { index, recomendacion ->
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    fraseSeleccionada = frasesPorRecomendacion[recomendacion]
                                },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF333333))
                        ) {
                            Text(
                                text = "${index + 1}. $recomendacion",
                                modifier = Modifier.padding(16.dp),
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }

            fraseSeleccionada?.let { frase ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(6.dp, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1F1F))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "💡 Frase para ti",
                                color = Color(0xFFFFC107),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = frase,
                                color = Color.LightGray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
