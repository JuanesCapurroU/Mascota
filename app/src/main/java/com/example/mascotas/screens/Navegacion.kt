package com.example.mascotas.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navegacion(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "screenA") {
        composable("screenA") { ScreenA(navController) }
        composable("screenB") { ScreenB(navController) }
    }
}
