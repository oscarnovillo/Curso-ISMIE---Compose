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
        screens = appDestinationList
    ) }
    val topBar : @Composable () -> Unit = { TopBar(
        navController = navController,
        screens = appDestinationList
    ) }


    NavHost(
        navController = navController,
        startDestination = Sumar.route,
    ) {
        composable(
            route= Sumar.route
        ) {
            SumarScreen(bottomBar = bottomBar,
                topBar = topBar,

            )

        }
        composable(
            route =  DetalleCoche.routeWithArgs,
            arguments = DetalleCoche.arguments
        ) {
            DetalleCochesScreen(
                cocheId = it.arguments?.getString(DetalleCoche.cocheIdArg) ?: "",
                bottomBar = bottomBar,
                topBar = topBar,
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composable(
            ListadoCoches.route
        ) {

            ListadoCochesScreen(
                bottomBar = bottomBar,
                topBar = topBar,
                onNavigateDetalle = {
                    navController.navigate("${DetalleCoche.route}/$it")
                }

            )

        }
        composable(
            ListadoUsers.route
        ) {

            ListadoUsersScreen(
                bottomBar = bottomBar,
                topBar = topBar,
                onNavigateDetalle = {
                    navController.navigate("${DetalleUser.route}/$it")
                }

            )

        }
        composable(
            route =  DetalleUser.routeWithArgs,
            arguments = DetalleUser.arguments
        ) {
            DetalleUserScreen(
                userId = it.arguments?.getString(DetalleUser.userIdArg) ?: "",
                bottomBar = bottomBar,
                topBar = topBar,
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }


    }



}
