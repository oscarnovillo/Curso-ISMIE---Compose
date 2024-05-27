package com.example.compose.ui.navigation

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument




val appDestinationList = listOf(Sumar,ListadoCoches,DetalleCoche,ListadoUsers,DetalleUser)
interface AppDestination{
    val route: String
    val title: String

}

interface AppMainBottomDestination : AppDestination {
    val onBottomBar: Boolean
    val icon: ImageVector
}

object Sumar : AppMainBottomDestination {
    override val route = "sumar"
    override val title = "Sumar"
    override val onBottomBar = true
    override val icon = Icons.Filled.Favorite
}

object ListadoCoches : AppMainBottomDestination {
    override val route = "listadoCoches"
    override val title = "Coches"
    override val onBottomBar = true
    override val icon = Icons.Rounded.AddCircle
}

object ListadoUsers : AppMainBottomDestination {
    override val route = "listadoUsers"
    override val title = "Users"
    override val onBottomBar = true
    override val icon = Icons.Outlined.Build
}

object DetalleCoche : AppDestination {
    override val route = "detalleCoche"
    override val title = "Detalle Coche"
    const val cocheIdArg = "cocheId"
    val routeWithArgs = "$route/{$cocheIdArg}"
    val arguments = listOf(
        navArgument(cocheIdArg) { type = NavType.StringType }
    )
}

object DetalleUser : AppDestination {
    override val route = "detalleUser"
    override val title = "Detalle User"
    const val userIdArg = "userId"
    val routeWithArgs = "${route}/{$userIdArg}"
    val arguments = listOf(
        navArgument(userIdArg) { type = NavType.StringType }
    )
}



