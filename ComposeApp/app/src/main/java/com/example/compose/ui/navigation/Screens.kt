package com.example.compose.ui.navigation

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument


val screensList = listOf(Sumar,ListadoCoches,DetalleCoche,ListadoUsers,DetalleUser)

val screens = listOf(
    Screens("sumar","Sumar",true,icon = Icons.Filled.Favorite),
    Screens("listadoCoches","coches",true,icon = Icons.Rounded.AddCircle),
    Screens("detalleCoche/{cocheId}","detalle coche"),
    Screens("listadoUsers","Users",true,icon = Icons.Outlined.Build),
    Screens("detalleUser/{userId}","detalle user"),
)

data class Screens(val route: String,
    val title: String = route,
    val onBottomBar: Boolean = false,
    val icon: ImageVector? = null)




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
    override val route = "detalleCoche/{cocheId}"
    override val title = "detalle coche"
    const val cocheIdArg = "cocheId"
    val routeWithArgs = "$route/{$cocheIdArg}"
    val arguments = listOf(
        navArgument(cocheIdArg) { type = NavType.StringType }
    )
}

object DetalleUser : AppDestination {
    override val route = "detalleUser/{userId}"
    override val title = "detalle user"
    const val userIdArg = "userId"
    val routeWithArgs = "${route}/{$userIdArg}"
    val arguments = listOf(
        navArgument(userIdArg) { type = NavType.StringType }
    )
}



