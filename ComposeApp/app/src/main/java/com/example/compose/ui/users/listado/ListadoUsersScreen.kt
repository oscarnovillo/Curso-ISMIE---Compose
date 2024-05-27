package com.example.compose.ui.users.listado

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.ui.common.UiEvent
import com.example.compose.ui.users.listado.ListadoContract.*


@Composable
fun ListadoUsersScreen(
    listadoViewModel: ListadoViewModel = hiltViewModel(),
    bottomBar : @Composable () -> Unit = {},
    topBar : @Composable () -> Unit = {},
    onNavigateDetalle : (String) -> Unit = {},


    ) {
    val uiState by listadoViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    ListadoContent(
        uiState = uiState,
        bottomBar = bottomBar,
        topBar = topBar,
        onNavigateDetalle = onNavigateDetalle,
        snackbarHostState = snackbarHostState,

    )

    LaunchedEffect(uiState.uiEvent) {
        uiState.uiEvent?.let {
            if (it is UiEvent.ShowSnackbar) {
                snackbarHostState.showSnackbar(it.message)
            }
            listadoViewModel.handleEvent(ListadoEvent.UiEventDone)
        }
    }

}

@Composable
fun ListadoContent(
    uiState: ListadoState,
    onNavigateDetalle: (String) -> Unit,
    bottomBar: @Composable () -> Unit = {},
    topBar : @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    ) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomBar,
        topBar = topBar,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Listado de users",
                modifier = Modifier.clickable { onNavigateDetalle("1234") })
        }

    }

}
