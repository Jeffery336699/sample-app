import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Preview
@Composable
fun CounterComponent() {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        var counter by remember { mutableStateOf(0) }

        Text( //1
            "Click the buttons to adjust your value:",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text( //2
            "$counter",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = typography.h3
        )

        Row {
            Button(
                onClick = { counter-- },
                Modifier.weight(1f)
            ) {
                Text("-")
            }
            Spacer(Modifier.width(16.dp))
            Button(
                onClick = { counter++ },
                Modifier.weight(1f)
            ) {
                Text("+")
            }
        }
    }

}


@Composable
fun MovieScreen() {
    val list = List(10) {
        it
    }
    for (i in list) {
        // Optimize: 使用i（当做id）作为Composable的索引，为的是优化重组
        key(i) {
            MovieOverview(i)
        }
    }
}

@Composable
fun MovieOverview(any: Any) {
    LaunchedEffect(Unit){
        snapshotFlow { any }.collect {

        }
        println("xxxxx")

    }
}





