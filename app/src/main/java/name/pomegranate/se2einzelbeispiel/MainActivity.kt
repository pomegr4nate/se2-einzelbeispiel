package name.pomegranate.se2einzelbeispiel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import name.pomegranate.se2einzelbeispiel.helpers.NetHelper
import name.pomegranate.se2einzelbeispiel.helpers.SortHelper
import name.pomegranate.se2einzelbeispiel.ui.theme.SE2EinzelbeispielTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SE2EinzelbeispielTheme {
                NetworkTestView()
            }
        }
    }
}

@Composable
fun NetworkTestView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Header()
        Form()
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "SE2 NetworkTest",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(20.dp)
        )
    }
}

@Composable
fun Form() {
    Column {
        Text(
            text = "Gib deine Matrikelnummer ein:",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        var matrikelnummer by rememberSaveable {
            mutableStateOf("")
        }

        TextField(
            value = matrikelnummer,
            onValueChange = {
                // Only allow digits to be input
                if (it.isDigitsOnly()) {
                    matrikelnummer = it
                }
            },
            label = { Text(text = "Matrikelnummer") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        )

        Text(
            text = "Antwort vom Server:",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, top = 30.dp)
        )

        var result by rememberSaveable {
            mutableStateOf("")
        }

        Text(
            text = result,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(10.dp)
        )

        val ioscope = CoroutineScope(Dispatchers.IO)

        Button(
            onClick = {
                ioscope.launch {
                    result = NetHelper.getFromServer(matrikelnummer)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            Text(text = "Absenden")
        }

        Button(
            onClick = {
                matrikelnummer = SortHelper.sortMatrikelnummer(matrikelnummer)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            Text(text = "Matrikelnummer Sortieren")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkTestViewPreview() {
    SE2EinzelbeispielTheme {
        NetworkTestView()
    }
}