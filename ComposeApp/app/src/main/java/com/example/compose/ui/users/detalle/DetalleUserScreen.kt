package com.example.compose.ui.users.detalle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.ui.coches.detalle.DetalleContract
import com.example.compose.ui.common.UiEvent
import com.example.compose.ui.users.detalle.DetalleUsersContract.DetalleUsersEvent

@Composable
fun DetalleUserScreen(
    userId: String,
    detalleUserViewModel: DetalleUsersViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},


    ) {

    val uiState by detalleUserViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    DetalleUserContent(
        uiState = uiState,
        userId = userId,
        bottomBar = bottomBar,
        topBar = topBar,
        snackbarHostState = snackbarHostState,
        onNavigateBack = onNavigateBack,


        )

    LaunchedEffect(uiState.uiEvent) {
        uiState.uiEvent?.let {
            if (it is UiEvent.ShowSnackbar) {
                snackbarHostState.showSnackbar(it.message)
            } else if (it is UiEvent.Navigate) {
                onNavigateBack()
            }
            detalleUserViewModel.handleEvent(DetalleUsersEvent.UiEventDone)
        }
    }


}

@Composable
fun DetalleUserContent(
    uiState: DetalleUsersContract.DetalleUsersState,
    userId: String,
    bottomBar: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomBar,
        topBar = topBar,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text("Detalle de user ${userId}",
                modifier = Modifier.clickable { onNavigateBack() })
        }

    }

}
