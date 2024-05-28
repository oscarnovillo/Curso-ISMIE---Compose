package com.example.compose.ui.coches.detalle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.domain.modelo.Coche
import com.example.compose.ui.coches.detalle.DetalleContract.DetalleEvent
import com.example.compose.ui.common.UiEvent


@Composable
fun DetalleCochesScreen(
    cocheId: String,
    detalleViewModel: DetalleViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    showSnackbar: (String) -> Unit = {},

    ) {
    val uiState by detalleViewModel.uiState.collectAsState()


    LaunchedEffect(Unit) {
        detalleViewModel.handleEvent(DetalleEvent.GetCoche(cocheId))
    }

    DetalleContent(
        coche = uiState.coche,
        onDelete = { detalleViewModel.handleEvent(DetalleEvent.DelCoche) },


        )

    LaunchedEffect(uiState.uiEvent) {
        uiState.uiEvent.forEach {
            if (it is UiEvent.ShowSnackbar) {
                showSnackbar(it.message)
            } else if (it is UiEvent.Navigate) {
                onNavigateBack()
            }
            detalleViewModel.handleEvent(DetalleEvent.UiEventDone)
        }
    }

}

@Composable
fun DetalleContent(
    coche: Coche?,
    onDelete: () -> Unit = {},
) {

    Box() {
        Text("Detalle de coches ${coche?.matricula}",
            modifier = Modifier.clickable { onDelete() })
    }


}
