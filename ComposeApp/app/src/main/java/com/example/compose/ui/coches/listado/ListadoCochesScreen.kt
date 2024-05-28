package com.example.compose.ui.coches.listado

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.domain.modelo.Coche
import com.example.compose.ui.common.SwipeToDeleteContainer
import com.example.compose.ui.common.UiEvent
import java.time.LocalDate


@Composable
fun ListadoCochesScreen(
    listadoViewModel: ListadoViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    onNavigateDetalle: (String) -> Unit = {},


    ) {
    val uiState by listadoViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    ListadoContent(
        coches = uiState.coches,
        bottomBar = bottomBar,
        topBar = topBar,
        onNavigateDetalle = onNavigateDetalle,
        snackbarHostState = snackbarHostState,
        onDeleteCoche = { coche -> listadoViewModel.handleEvent(ListadoEvent.DeleteCoche(coche)) }

        )

    LaunchedEffect(uiState.event) {
        uiState.event?.let {
            if (it is UiEvent.ShowSnackbar) {
                snackbarHostState.showSnackbar(it.message)
            }
            listadoViewModel.handleEvent(ListadoEvent.UiEventDone)
        }
    }

}

@Composable
fun ListadoContent(
    coches: List<Coche>,
    onNavigateDetalle: (String) -> Unit,
    bottomBar: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    onDeleteCoche: (Coche) -> Unit,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomBar,
        topBar = topBar,
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            this.items(items = coches, key = { coche -> coche.matricula }) { coche ->
                CocheItem(coche = coche, onNavigateDetalle = onNavigateDetalle,onDeleteCoche= onDeleteCoche)
            }

        }

    }

}

@Composable
fun CocheItem(
    coche: Coche,
    onNavigateDetalle: (String) -> Unit,
    onDeleteCoche: (Coche) -> Unit,
) {
    SwipeToDeleteContainer(item =coche , onDelete = onDeleteCoche) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(1.dp)
            .clickable { onNavigateDetalle(coche.matricula) }) {
            Row(
                modifier = Modifier
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(),


                    text = coche.matricula
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.7F)
                        .padding(4.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = coche.marca
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = coche.modelo,

                        )
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewListadoCochesScreen() {
    ListadoContent(
        coches = listOf(
            Coche("1234", "Rojo", "Seat", "red", 111.0, LocalDate.now(), 100),
            Coche("5678", "Azul", "Renault", "blue", 222.0, LocalDate.now(), 200),
            Coche("9876", "Verde", "Ford", "green", 333.0, LocalDate.now(), 300),
        ),
        onNavigateDetalle = {},
        onDeleteCoche = {},


    )
}


