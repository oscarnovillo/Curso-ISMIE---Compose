package com.example.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.ui.coches.detalle.DetalleCochesScreen
import com.example.compose.ui.coches.listado.ListadoCochesScreen
import com.example.compose.ui.common.BottomBar
import com.example.compose.ui.common.TopBar
import com.example.compose.ui.sumar.SumarScreen
import com.example.compose.ui.users.detalle.DetalleUserScreen
import com.example.compose.ui.users.listado.ListadoUsersScreen


@Composable
fun Navigation()
{
    val navController = rememberNavController()

    val bottomBar : @Composable () -> Unit ={ BottomBar(
        navController = navController,
        screens = screens
    ) }
    val topBar : @Composable () -> Unit = { TopBar(
        navController = navController,
        screens = screens
    ) }


    NavHost(
        navController = navController,
        startDestination = "sumar",
    ) {
        composable(
            route= "sumar"
        ) {
            SumarScreen(bottomBar = bottomBar,
                topBar = topBar,

            )

        }
        composable(
            route =  "detalleCoche/{cocheId}",
            arguments = listOf(
                navArgument(name = "cocheId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            DetalleCochesScreen(
                cocheId = it.arguments?.getString("cocheId") ?: "",
                bottomBar = bottomBar,
                topBar = topBar,
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composable(
            "listadoCoches"
        ) {

            ListadoCochesScreen(
                bottomBar = bottomBar,
                topBar = topBar,
                onNavigateDetalle = {
                    navController.navigate("detalleCoche/$it")
                }

            )

        }
        composable(
            "listadoUsers"
        ) {

            ListadoUsersScreen(
                bottomBar = bottomBar,
                topBar = topBar,
                onNavigateDetalle = {
                    navController.navigate("detalleUser/$it")
                }

            )

        }
        composable(
            route =  "detalleUser/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            DetalleUserScreen(
                userId = it.arguments?.getString("userId") ?: "",
                bottomBar = bottomBar,
                topBar = topBar,
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }


    }



}
