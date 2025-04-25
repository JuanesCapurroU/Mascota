package com.example.mascotas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mascotas.items.mascotasRegistradas
import com.example.mascotas.models.Mascota
import kotlinx.coroutines.launch

@Composable
fun ScreenB(navController: NavController) {
    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }
    var mascotaAEliminar by remember { mutableStateOf<Mascota?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = if (mascotaSeleccionada == null) "Mascotas Registradas" else "Detalles de la Mascota",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (mascotaSeleccionada == null) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(mascotasRegistradas) { mascota ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { mascotaSeleccionada = mascota },
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = mascota.nombre.take(2).uppercase(),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("🐾 ${mascota.nombre}", style = MaterialTheme.typography.titleMedium)
                                    Text("Raza: ${mascota.raza}", fontSize = 14.sp)
                                }
                                IconButton(onClick = {
                                    mascotaAEliminar = mascota
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
                            }
                        }
                    }
                }
            } else {
                var modoEdicion by remember { mutableStateOf(false) }

                if (modoEdicion) {
                    var nombre by remember { mutableStateOf(mascotaSeleccionada!!.nombre) }
                    var raza by remember { mutableStateOf(mascotaSeleccionada!!.raza) }
                    var tamaño by remember { mutableStateOf(mascotaSeleccionada!!.tamaño) }
                    var edad by remember { mutableStateOf(mascotaSeleccionada!!.edad.toString()) }
                    var imagenUrl by remember { mutableStateOf(mascotaSeleccionada!!.imagenUrl) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Editar Mascota", style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                            OutlinedTextField(value = raza, onValueChange = { raza = it }, label = { Text("Raza") })
                            OutlinedTextField(value = tamaño, onValueChange = { tamaño = it }, label = { Text("Tamaño") })
                            OutlinedTextField(value = edad, onValueChange = { edad = it }, label = { Text("Edad") })
                            OutlinedTextField(value = imagenUrl, onValueChange = { imagenUrl = it }, label = { Text("Imagen URL") })

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                                Button(onClick = {
                                    mascotaSeleccionada?.let {
                                        it.nombre = nombre
                                        it.raza = raza
                                        it.tamaño = tamaño
                                        it.edad = edad.toIntOrNull()?.toString() ?: it.edad
                                        it.imagenUrl = imagenUrl
                                        modoEdicion = false
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Mascota actualizada")
                                        }
                                    }
                                }) {
                                    Text("Guardar")
                                }


                                OutlinedButton(onClick = {
                                    modoEdicion = false
                                }) {
                                    Text("Cancelar")
                                }
                            }
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = mascotaSeleccionada!!.imagenUrl,
                                contentDescription = "Imagen Mascota",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .padding(bottom = 16.dp)
                            )
                            Text("Nombre: ${mascotaSeleccionada!!.nombre}", style = MaterialTheme.typography.titleMedium)
                            Text("Raza: ${mascotaSeleccionada!!.raza}", fontSize = 14.sp)
                            Text("Tamaño: ${mascotaSeleccionada!!.tamaño}", fontSize = 14.sp)
                            Text("Edad: ${mascotaSeleccionada!!.edad} años", fontSize = 14.sp)

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(onClick = { modoEdicion = true }) {
                                Text("✏️ Editar")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (mascotaSeleccionada != null) {
                        mascotaSeleccionada = null
                    } else {
                        navController.navigate("screenA")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (mascotaSeleccionada != null) "← Volver a la lista" else "← Volver al registro",
                    fontSize = 16.sp
                )
            }
        }

        // Diálogo de confirmación
        if (mascotaAEliminar != null) {
            AlertDialog(
                onDismissRequest = { mascotaAEliminar = null },
                title = { Text("Confirmar eliminacion") },
                text = { Text("Estas seguro de que deseas eliminar a ${mascotaAEliminar!!.nombre}?") },
                confirmButton = {
                    TextButton(onClick = {
                        mascotasRegistradas.remove(mascotaAEliminar)
                        scope.launch {
                            snackbarHostState.showSnackbar("Mascota eliminada")
                        }
                        mascotaAEliminar = null
                    }) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mascotaAEliminar = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
