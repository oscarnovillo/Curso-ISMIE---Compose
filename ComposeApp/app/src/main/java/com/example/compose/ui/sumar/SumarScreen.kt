package com.example.compose.ui.sumar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SumarScreen(

    sumaViewModel: SumaViewModel = hiltViewModel()

) {
    val uiState by sumaViewModel.uiState.collectAsState()
    val uiError by sumaViewModel.uiError.collectAsState(
        initial = null,
        context = Dispatchers.Main.immediate,
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    SumarContent(
        uiState = uiState,
        onSumar = { incremento -> sumaViewModel.handleEvent(SumaEvent.Sumar(incremento)) },
        onRestar = { incremento -> sumaViewModel.handleEvent(SumaEvent.Restar(incremento)) },
        snackbarHostState = snackbarHostState,
    )

    uiError?.let {
        scope.launch {
            snackbarHostState.showSnackbar(it)
            sumaViewModel.handleEvent(SumaEvent.ErrorMostrado)
        }

    }

}

@Composable
fun SumarContent(
    uiState: SumaState,
    onSumar: (Int) -> Unit = {},
    onRestar: (Int) -> Unit = {},
    snackbarHostState: SnackbarHostState = SnackbarHostState(),

    ) {

    Box(modifier = Modifier.fillMaxSize()) {


        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()

        ) {

            Text(
                text = uiState.contador.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
            Row(modifier = Modifier.padding(8.dp)) {
                Button(onClick = { onSumar(1) }) {
                    Text(
                        text = "Sumar"
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Button(onClick = { onRestar(1) }) {
                    Text(
                        text = "Restar"
                    )
                }
            }


        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    fontScale = 1.5f,
    name = "Light Mode"

)
@Composable
fun PreviewSumarScreen() {
    var uiState = SumaState(1, null)
    SumarContent(uiState = uiState, onSumar = { })
}