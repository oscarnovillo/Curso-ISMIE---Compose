package com.example.compose.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.ui.coches.detalle.DetalleCochesScreen
import com.example.compose.ui.coches.listado.ListadoCochesScreen
import com.example.compose.ui.common.BottomBar
import com.example.compose.ui.common.TopBar
import com.example.compose.ui.sumar.SumarScreen
import com.example.compose.ui.users.detalle.DetalleUserScreen
import com.example.compose.ui.users.listado.ListadoUsersScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation()
{
    val navController = rememberNavController()
    val topAppbarState by remember {
        mutableStateOf(TopBarState())
    }


    val bottomBar : @Composable () -> Unit ={ BottomBar(
        navController = navController,
        screens = appDestinationList
    ) }
    val topBar : @Composable () -> Unit = { TopBar(
        topBarState = topAppbarState,
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
            topAppbarState.arrangement = Arrangement.Start
            SumarScreen(bottomBar = bottomBar,
                topBar = topBar,

            )

        }
        composable(
            route =  DetalleCoche.routeWithArgs,
            arguments = DetalleCoche.arguments
        ) {
            topAppbarState.arrangement = Arrangement.Center

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
            topAppbarState.arrangement = Arrangement.Center

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
            topAppbarState.arrangement = Arrangement.Center
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
            topAppbarState.arrangement = Arrangement.Center
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


