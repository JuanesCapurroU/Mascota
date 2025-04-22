package com.example.mascotas.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mascotas.items.mascotasRegistradas
import com.example.mascotas.models.Mascota


@Composable
fun ScreenB(navController: NavController) {
    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        if (mascotaSeleccionada == null) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(mascotasRegistradas) { mascota ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { mascotaSeleccionada = mascota }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Nombre: ${mascota.nombre}")
                            Text("Raza: ${mascota.raza}")
                        }
                    }
                }
            }
        } else {
            // Tarjeta con detalles e imagen
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
                            .height(200.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Nombre: ${mascotaSeleccionada!!.nombre}")
                    Text("Raza: ${mascotaSeleccionada!!.raza}")
                    Text("Tamaño: ${mascotaSeleccionada!!.tamaño}")
                    Text("Edad: ${mascotaSeleccionada!!.edad}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (mascotaSeleccionada != null) {
                    mascotaSeleccionada = null
                } else {
                    navController.navigate("screenA")
                }
            }
        ) {
            Text(if (mascotaSeleccionada != null) "Volver a la lista" else "Volver al registro")
        }
    }
}
