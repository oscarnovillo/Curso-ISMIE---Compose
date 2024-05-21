package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MyScreen("abkabakbjkja")
        }
    }
}


@Composable
fun MyScreen(
    name : String,

) {
    ComposeAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,

                modifier = Modifier.fillMaxSize()
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.error),

                ) {
                Greeting(name = name,
                        modifier = Modifier.align(Alignment.Center)
                            .offset(y = -200.dp)
                            .border(2.dp, MaterialTheme.colorScheme.primary)
                            .padding(16.dp)
                    )
                Greeting(name = "otro nombre")
                Row( modifier = Modifier.align(Alignment.TopStart)){
                    Greeting(name = "row1", modifier = Modifier.padding(16.dp)
                        .weight(0.3f))
                    Greeting(name = "row2",
                            modifier = Modifier.padding(16.dp)
                            .weight(0.1f)
                                )
                }
            }
        }
    }
    
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",

    )
}




class PreviewStateParameter: PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf("World", "Android")
}



@Preview(
    showBackground = true,
    showSystemUi = true,
    fontScale = 1.5f,
    name = "Light Mode"
)

// PreviewWindowsSize, fontScale, lightdark....
@Composable
fun GreetingPreview(
    @PreviewParameter(PreviewStateParameter::class)  name: String
) {
    MyScreen(name)
}