package com.example.bienestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bienestapp.viewmodel.ChatIAViewModel
import com.example.bienestapp.viewmodel.ChatIAViewModel.ModeloIA
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatIAViewModel) {
    var mensaje by remember { mutableStateOf("") }
    val historial by viewModel.historial.collectAsState()
    val mensajeInicial by viewModel.mensajeInicial.collectAsState()
    val isCargando by viewModel.isCargando.collectAsState()
    var modeloSeleccionado by remember { mutableStateOf(ModeloIA.LLAMA) }

    LaunchedEffect(mensajeInicial) {
        mensajeInicial?.let {
            if (it.isNotBlank()) {
                viewModel.enviarMensaje(it, modeloSeleccionado)
                viewModel.setMensajeInicial("")
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Asistente IA", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF121212))
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Black, Color(0xFF1C1C1C))
                    )
                )
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Modelo: ", modifier = Modifier.alignByBaseline(), color = Color.White)
                DropdownMenuBox(
                    selectedModelo = modeloSeleccionado,
                    onModeloSelected = { modeloSeleccionado = it }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(historial) { (pregunta, respuesta) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ChatBubble(text = pregunta, isUser = true)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        ChatBubble(text = respuesta, isUser = false)
                    }
                }

                if (isCargando) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            TypingBubble()
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = mensaje,
                onValueChange = { mensaje = it },
                label = {
                    Text(
                        text = "¿En qué te puedo ayudar?",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Gray,
                    focusedBorderColor = Color(0xFF009688),
                    cursorColor = Color.White,
                    unfocusedContainerColor = Color(0xFF2A2A2A),
                    focusedContainerColor = Color(0xFF2A2A2A)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (mensaje.isNotBlank()) {
                        viewModel.enviarMensaje(mensaje, modeloSeleccionado)
                        mensaje = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF009688),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Enviar a la IA")
            }
        }
    }
}

@Composable
fun ChatBubble(text: String, isUser: Boolean) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = if (isUser) Color(0xFF00796B) else Color(0xFF333333),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .widthIn(max = 280.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp),
            color = Color.White
        )
    }
}

@Composable
fun DropdownMenuBox(
    selectedModelo: ModeloIA,
    onModeloSelected: (ModeloIA) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedModelo.name)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            ModeloIA.values().forEach { modelo ->
                DropdownMenuItem(
                    text = { Text(modelo.name) },
                    onClick = {
                        onModeloSelected(modelo)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TypingBubble() {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = Color(0xFF444444),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .widthIn(max = 200.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(3) {
                Dot()
            }
        }
    }
}

@Composable
fun Dot() {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            visible = !visible
            delay(300)
        }
    }

    Box(
        modifier = Modifier
            .size(8.dp)
            .background(
                color = if (visible) Color.White else Color.Gray,
                shape = MaterialTheme.shapes.small
            )
    )
}
